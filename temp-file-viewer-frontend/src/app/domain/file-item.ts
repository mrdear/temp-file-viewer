export class FileItem {

  fileName: string;

  md5Name: string;

  frontRoute: string;

  passwd: string;

  fileType: string;

  fileContent: string;

  getFilePath(): string {
    switch (this.fileType) {
      case '.md':
        return `${this.frontRoute}?name=${this.fileName}&passwd=${this.passwd}`;
    }
  }

}
