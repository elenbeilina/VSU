import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AddUserModalComponent} from './add-user-modal/add-user-modal.component';

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

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openUserModal() {
    this.dialog.open(AddUserModalComponent, {
      width: '400px'
    });
  }


}
