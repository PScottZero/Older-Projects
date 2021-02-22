import { Injectable } from '@angular/core';
import {BlockQueue} from '../objects/BlockQueue';
import {Tetromino} from '../objects/Tetromino';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  blockQueue: BlockQueue;
  score: number;

  cycleBlockQueue(): Tetromino {
    return this.blockQueue.cycle();
  }

  getBlock(index: number) {
    return this.blockQueue.getBlock(index);
  }

  resetBlockQueue() {
    this.blockQueue = new BlockQueue();
  }

  getScore(): number {
    return this.score;
  }

  getFormattedScore(): string {
    let scoreString = this.score.toString();
    const size = 6 - scoreString.length;
    for (let i = 0; i < size; i++) {
      scoreString = '0' + scoreString;
    }
    return scoreString;
  }

  addScore(updatedScore: number) {
    this.score += updatedScore;
  }

  resetScore() {
    this.score = 0;
  }
}
