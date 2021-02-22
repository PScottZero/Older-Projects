import {Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {Tetromino} from '../../objects/Tetromino';
import {ScoreCutoff} from '../../enum/ScoreCutoff';
import {Speed} from '../../enum/Speed';
import {GameService} from '../../services/game.service';

const BLOCK_SIZE = 30;
const PF_WIDTH = 300;
const PF_HEIGHT = 600;

@Component({
  selector: 'app-playfield',
  templateUrl: './playfield.component.html',
  styleUrls: ['./playfield.component.scss']
})
export class PlayfieldComponent implements OnInit {
  context: CanvasRenderingContext2D;
  currBlock: Tetromino;
  blockRow: number;
  blockCol: number;
  isRunning: boolean;
  isPaused: boolean;
  isFirstGame: boolean;
  grid: number[][];
  runInterval: any;
  speed: number;

  constructor(private gameService: GameService) {}

  ngOnInit() {
    this.context = (document.getElementById('playfield') as HTMLCanvasElement).getContext('2d');
    this.isFirstGame = true;
  }

  checkDown(oldR: number) {
    console.log("hello");
    if (oldR === this.blockRow) {
      this.stop();
      if (this.blockRow === 0) {
        this.isRunning = false;
      } else {
        this.mergeCurrentBlockWithPlayfield();
        this.clearLines();
        this.currBlock = this.gameService.cycleBlockQueue();
        this.blockRow = 0;
        this.blockCol = 3;
        this.run();
      }
    }
  }

  clearLines() {
    let count = 0;
    for (let r = 0; r < this.grid.length; r++) {

      // check if line is cleared, then remove line
      if (!this.grid[r].includes(0)) {
        for (let i = r; i > 0; i--) {
          this.grid[i] = this.grid[i - 1];
        }
        this.grid[0] = new Array(10).fill(0);

        // add 10 points to score if line cleared
        count += 10;
      }
    }

    // score multiplier based on how many lines were cleared simultaneously
    if (count === 2) {
      count *= 2;
    } else if (count === 3) {
      count *= 3;
    } else if (count === 4) {
      count *= 4;
    }
    this.gameService.addScore(count);
  }

  generateBlankPlayfield() {
    this.grid = new Array(20);
    for (let i = 0; i < this.grid.length; i++) {
      this.grid[i] = new Array(10).fill(0);
    }
  }

  restart() {
    clearInterval(this.runInterval);
    this.isRunning = false;
    this.currBlock = this.gameService.cycleBlockQueue();
    this.blockRow = 0;
    this.blockCol = 3;
    this.isPaused = false;
    this.speed = Speed.SPEED_0;
    this.gameService.resetBlockQueue();
    this.gameService.resetScore();
    this.generateBlankPlayfield();
    this.drawPlayfield();
  }

  run() {
    this.isRunning = true;
    this.runInterval = setInterval(() => {
      this.step();
    }, this.speed);
  }

  pause() {
    this.isPaused = !this.isPaused;
    if (!this.isPaused) {
      this.run();
    } else {
      this.stop();
    }
  }

  stop() {
    clearInterval(this.runInterval);
  }

  step() {
    if (this.isRunning) {
      if (!this.isPaused) {
        if (this.gameService.getScore() >= ScoreCutoff.CUTOFF_5) {
          this.speed = Speed.SPEED_5;
        } else if (this.gameService.getScore() >= ScoreCutoff.CUTOFF_4) {
          this.speed = Speed.SPEED_4;
        } else if (this.gameService.getScore() >= ScoreCutoff.CUTOFF_3) {
          this.speed = Speed.SPEED_3;
        } else if (this.gameService.getScore() >= ScoreCutoff.CUTOFF_2) {
          this.speed = Speed.SPEED_2;
        } else if (this.gameService.getScore() >= ScoreCutoff.CUTOFF_1) {
          this.speed = Speed.SPEED_1;
        }

        const oldR = this.blockRow;
        this.down();
        this.checkDown(oldR);
        this.drawPlayfield();
      }
    }
  }

  checkForCollision(): boolean {
    for (let r = 0; r < this.currBlock.grid.length; r++) {
      for (let c = 0; c < this.currBlock.grid[r].length; c++) {
        if (this.currBlock.grid[r][c] !== 0) {

          // check out of bounds
          if (this.blockCol + c < 0 ||
            this.blockCol + c >= this.grid[0].length ||
            this.blockRow + r >= this.grid.length) {
            return true;
          }

          // check block checkForCollision
          if (this.grid[this.blockRow + r][this.blockCol + c] !== 0) {
            return true;
          }
        }
      }
    }
    return false;
  }

  mergeCurrentBlockWithPlayfield() {
    for (let r = 0; r < this.currBlock.grid.length; r++) {
      for (let c = 0; c < this.currBlock.grid[r].length; c++) {
        if (this.currBlock.grid[r][c] !== 0) {
          this.grid[this.blockRow + r][this.blockCol + c] = this.currBlock.grid[r][c];
        }
      }
    }
  }

  drawPlayfield() {

    // clear canvas
    this.context.clearRect(0, 0, PF_WIDTH, PF_HEIGHT);
    this.context.beginPath();

    // drawPlayfield previous blocks on playfield grid
    for (let r = 0; r < this.grid.length; r++) {
      for (let c = 0; c < this.grid[r].length; c++) {
        this.context.fillStyle = Tetromino.BLOCK_COLORS[this.grid[r][c]];
        this.context.fillRect(c * BLOCK_SIZE + 1, r * BLOCK_SIZE + 1,
          BLOCK_SIZE - 2, BLOCK_SIZE - 2);
      }
    }

    // draw current block onto playfield
    this.currBlock.draw(this.context, this.blockRow, this.blockCol, BLOCK_SIZE, Tetromino.BLOCK_COLORS);

    // font styling
    this.context.fillStyle = '#FFFFFF';
    this.context.font = '48px Arial';

    // draws game over or pause onto playfield
    if (!this.isRunning && !this.isFirstGame) {
      this.context.fillText('Game Over', 30, 300);
    } else if (this.isPaused) {
      this.context.fillText('Pause', 85, 300);
    } else if (this.isFirstGame) {
      this.context.fillText('Press \'N\'', 60, 275);
      this.context.fillText('To Start', 65, 325);
    }
  }

  left() {
    this.blockCol--;
    if (this.checkForCollision()) {
      this.blockCol++;
    }
  }

  right() {
    this.blockCol++;
    if (this.checkForCollision()) {
      this.blockCol--;
    }
  }

  down() {
    this.blockRow++;
    if (this.checkForCollision()) {
      this.blockRow--;
    }
  }

  clockwise() {
    this.currBlock.rotateCW();
    if (this.checkForCollision()) {
      this.currBlock.rotateCCW();
    }
  }

  counterClockwise() {
    this.currBlock.rotateCCW();
    if (this.checkForCollision()) {
      this.currBlock.rotateCW();
    }
  }

  @HostListener('window:keydown', ['$event'])
  keyDown(event: KeyboardEvent) {
    switch (event.key) {
      case ' ':
        this.clockwise();
        break;
      case 'Alt':
        this.counterClockwise();
        break;
      case 'ArrowLeft':
        this.left();
        break;
      case 'ArrowRight':
        this.right();
        break;
      case 'ArrowDown':
        this.down();
        break;
      case 'n':
        if (this.isFirstGame) {
          this.isFirstGame = false;
          this.restart();
          this.run();
        }
        break;
      case 'r':
        this.restart();
        break;
      case 'p':
        if (this.isRunning) {
          this.pause();
        }
        break;
    }
    this.drawPlayfield();
  }
}
