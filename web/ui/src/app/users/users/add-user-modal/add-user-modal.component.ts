import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormMode} from '../../../common/misc/helper';

interface InputData {
  mode: FormMode;
  element: any;
}

@Component({
  selector: 'app-add-user-modal',
  templateUrl: './add-user-modal.component.html',
  styleUrls: ['./add-user-modal.component.scss']
})
export class AddUserModalComponent implements OnInit {

  FormMode = FormMode;

  constructor(public dialogRef: MatDialogRef<AddUserModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data: InputData) { }

  ngOnInit(): void {
  }

}
