import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material";

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor(private snackBar: MatSnackBar) { }

  toast(message: string) {
    this.snackBar.open(message, 'Hide', {duration: 2000});
  }

}
