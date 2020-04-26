import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {SignInComponent} from '../sign-in/sign-in.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private modalDialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  openSignIn() {
    this.modalDialog.open(SignInComponent, {
      width: '370px',
      autoFocus: false,
    }).afterClosed().subscribe(res => {
    });
  }

}
