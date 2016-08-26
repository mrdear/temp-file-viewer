package cn.mrdear.job;

/**
 * 打包成jar后无法写入文件到jar,所以该类放弃掉
 * @author Niu Li
 * @date 2016/8/25
 */
//@Component("CategoryJob")
public class CategoryJob{

//    private static Logger logger = LoggerFactory.getLogger(CategoryJob.class);
//
//    private static final String RELATIVEPATH = "/static/json/category.json";
//
//    @Resource(name = "fileService")
//    private FileService fileService;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        writeCategory();
//    }
//
//    /**
//     * 写入目录到文件中
//     */
//    public void writeCategory(){
//        ArrayList<String> pathList = fileService.findFilesPath();//存放路径集合
//        ArrayList<Category> categories = new ArrayList<>();//存放目录集合
//        //遍历,java8的方法
////        pathList.forEach(path->{
////            Category category = fileService.iteratorFile(path);
////            categories.add(category);
////        });
//        for (String path : pathList) {
//            Category category = fileService.iteratorFile(path);
//            categories.add(category);
//        }
//        //定制序列化
//        String result = fileService.toTreeJsonStr(categories);
//        //写入到文件
//
//        String path = this.getClass().getResource("/").getPath();
//        File file = new File(path+RELATIVEPATH);
//        try {
//            FileUtils.write(file,result,"UTF-8");
//            logger.info("写入目录成功");
//        } catch (IOException e) {
//            logger.error("写入文件出错",e);
//        }
//    }
}
