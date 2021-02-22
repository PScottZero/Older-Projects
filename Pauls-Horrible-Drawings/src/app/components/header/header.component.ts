import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor() { }

  // shrinks header on scroll down
  ngOnInit() {
    window.onscroll = () => {
      const top = 50;
      if (document.body.scrollTop > top || document.documentElement.scrollTop > top) {
        document.getElementById('header').style.height = '3em';
      } else {
        document.getElementById('header').style.height = '6em';
      }
    };
  }
}
