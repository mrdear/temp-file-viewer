package cn.mrdear.filter;

/**
 *  Copyright 2003-2009 Terracotta, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import net.sf.ehcache.constructs.web.GenericResponseWrapper;
import net.sf.ehcache.constructs.web.ResponseUtil;
import net.sf.ehcache.constructs.web.filter.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides GZIP compression of responses.
 * <p/>
 * See the filter-mappings.xml entry for the gzip filter for the URL patterns which will be gzipped. At present this
 * includes .jsp, .js and .css.
 * <p/>
 *
 * @author <a href="mailto:gluck@thoughtworks.com">Greg Luck</a>
 * @author <a href="mailto:amurdoch@thoughtworks.com">Adam Murdoch</a>
 * @version $Id: GzipFilter.java 744 2008-08-16 20:10:49Z gregluck $
 */
public class GzipFilter extends Filter {

    private static final Logger LOG = LoggerFactory.getLogger(GzipFilter.class);
    private static final String VARY_HEADER_PARAM = "varyHeader";
    private static final String RETURN_ON_NOT_OK_PARAM = "returnOnNonOK";

    private boolean setVaryHeader;
    private boolean returnOnNonOk = true;

    /**
     * Performs initialisation.
     *
     * @param filterConfig
     */
    protected void doInit(FilterConfig filterConfig) throws Exception {
        String varyParam = filterConfig.getInitParameter(VARY_HEADER_PARAM);
        if (varyParam != null) {
            setVaryHeader = Boolean.valueOf(varyParam);
        }

        String returnOnNotOkParam = filterConfig.getInitParameter(RETURN_ON_NOT_OK_PARAM);
        if (returnOnNotOkParam != null) {
            returnOnNonOk = Boolean.valueOf(returnOnNotOkParam);
        }
    }

    /**
     * A template method that performs any Filter specific destruction tasks. Called from {@link #destroy()}
     */
    protected void doDestroy() {
        // noop
    }

    /**
     * Performs the filtering for a request.
     */
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws Exception {
        if (!isIncluded(request) && acceptsEncoding(request, "gzip") && !response.isCommitted()) {
            // Client accepts zipped content
            if (LOG.isDebugEnabled()) {
                LOG.debug(request.getRequestURL() + ". Writing with gzip compression");
            }

            // Create a gzip stream
            final ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            final GZIPOutputStream gzout = new GZIPOutputStream(compressed);

            // Handle the request
            final GenericResponseWrapper wrapper = new GenericResponseWrapper(response, gzout);
            wrapper.setDisableFlushBuffer(true);
            chain.doFilter(request, wrapper);
            wrapper.flush();

            gzout.close();

            // double check one more time before writing out
            // repsonse might have been committed due to error
            if (response.isCommitted()) {
                return;
            }

            // return on these special cases when content is empty or unchanged
            switch (wrapper.getStatus()) {
                case HttpServletResponse.SC_NO_CONTENT:
                case HttpServletResponse.SC_RESET_CONTENT:
                case HttpServletResponse.SC_NOT_MODIFIED:
                    return;
                default:
            }



            // Saneness checks
            byte[] compressedBytes = compressed.toByteArray();
            boolean shouldGzippedBodyBeZero = ResponseUtil.shouldGzippedBodyBeZero(compressedBytes, request);
            boolean shouldBodyBeZero = ResponseUtil.shouldBodyBeZero(request, wrapper.getStatus());
            if (shouldGzippedBodyBeZero || shouldBodyBeZero) {
                // No reason to add GZIP headers or write body if no content was written or status code specifies no
                // content
                response.setContentLength(0);
                return;
            }

            // Write the zipped body
            ResponseUtil.addGzipHeader(response);

            // Only write out header Vary as needed
            if (setVaryHeader) {
                ResponseUtil.addVaryAcceptEncoding(wrapper);
            }

            response.setContentLength(compressedBytes.length);

            response.getOutputStream().write(compressedBytes);

        } else {
            // Client does not accept zipped content - don't bother zipping
            if (LOG.isDebugEnabled()) {
                LOG.debug(request.getRequestURL() + ". Writing without gzip compression because the request does not accept gzip.");
            }
            chain.doFilter(request, response);
        }
    }

    /**
     * Checks if the request uri is an include. These cannot be gzipped.
     */
    private boolean isIncluded(final HttpServletRequest request) {
        final String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        final boolean includeRequest = !(uri == null);

        if (includeRequest && LOG.isDebugEnabled()) {
            LOG.debug(request.getRequestURL() + " resulted in an include request. This is unusable, because"
                    + "the response will be assembled into the overrall response. Not gzipping.");
        }
        return includeRequest;
    }

    /**
     * Determine whether the user agent accepts GZIP encoding. This feature is part of HTTP1.1. If a browser accepts
     * GZIP encoding it will advertise this by including in its HTTP header:
     * <p/>
     * <code>
     * Accept-Encoding: gzip
     * </code>
     * <p/>
     * Requests which do not accept GZIP encoding fall into the following categories:
     * <ul>
     * <li>Old browsers, notably IE 5 on Macintosh.
     * <li>Internet Explorer through a proxy. By default HTTP1.1 is enabled but disabled when going through a proxy. 90%
     * of non gzip requests seen on the Internet are caused by this.
     * </ul>
     * As of September 2004, about 34% of Internet requests do not accept GZIP encoding.
     *
     * @param request
     * @return true, if the User Agent request accepts GZIP encoding
     */
    protected boolean acceptsGzipEncoding(HttpServletRequest request) {
        return acceptsEncoding(request, "gzip");
    }

}

