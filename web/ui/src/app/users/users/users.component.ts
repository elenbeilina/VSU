import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AddUserModalComponent} from './add-user-modal/add-user-modal.component';
import {FormMode} from '../../common/misc/helper';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  dataSource = [
    {
      login: 'user',
      faculty: 'AMM',
      user: 'User 1',
      rating: 1200
    },
    {
      login: 'user',
      faculty: 'AMM',
      user: 'User 1',
      rating: 1200
    },
  ];
  displayedColumns = ['login', 'faculty', 'user', 'rating', 'actions'];
  FormMode = FormMode;

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openUserModal(mode: FormMode, element?) {
    this.dialog.open(AddUserModalComponent, {
      width: '400px',
      data: {
        mode,
        element
      }
    });
  }
}
