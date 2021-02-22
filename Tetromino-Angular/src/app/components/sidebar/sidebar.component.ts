import { Component } from '@angular/core';
import {GameService} from '../../services/game.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {

  constructor(private gameService: GameService) {}

  getScore() {
    if (this.gameService.score) {
      return this.gameService.getFormattedScore()
    } else {
      return '000000';
    }
  }
}
