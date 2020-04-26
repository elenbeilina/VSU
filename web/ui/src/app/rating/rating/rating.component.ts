import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent implements OnInit {

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
  displayedColumns = ['login', 'faculty', 'user', 'rating'];

  constructor() {
  }

  ngOnInit(): void {
  }

}
