import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {GameService} from '../../services/game.service';
import {BlockType} from '../../enum/BlockType';
import {Tetromino} from '../../objects/Tetromino';

@Component({
  selector: 'app-next-block',
  templateUrl: './next-block.component.html',
  styleUrls: ['./next-block.component.scss']
})
export class NextBlockComponent implements OnInit {
  context: CanvasRenderingContext2D;

  constructor(private gameService: GameService) {}

  ngOnInit() {
    this.context = (document.getElementById('next_block_canvas') as HTMLCanvasElement).getContext('2d');
    //this.drawNextBlocks();
  }

  drawNextBlocks() {
    this.context.clearRect(0, 0, 150, 475);
    this.context.beginPath();

    // drawPlayfield blocks onto panel
    for (let i = 0; i < 5; i++) {
      let offsetR = 2.5;
      let offsetC = 1.5;
      const block = this.gameService.getBlock(i);

      // drawing offsets for specific blocks
      if (block.type === BlockType.I_BLOCK || block.type === BlockType.O_BLOCK) { offsetC = 1; } // column offset for i-block and o-block
      if (block.type === BlockType.I_BLOCK) { offsetR = 1; } // row offset for i-block

      // drawPlayfield block onto next block panel
      block.draw(this.context, i * 3 + offsetR, offsetC, 25, Tetromino.BLOCK_COLORS);
    }
  }
}
