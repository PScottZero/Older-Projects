import {Component} from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {
  purpose = 'This website is less about my terrible art, and more about experimenting with web design';
  designedBy = 'Designed by Paul Scott using Angular';
  lastUpdated = 'Last updated 6 December 2019';
}
