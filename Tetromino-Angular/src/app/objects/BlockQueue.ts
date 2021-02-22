import {Tetromino} from './Tetromino';

const QUEUE_SIZE = 5;

export class BlockQueue {

  blockQueue: Tetromino[];

  constructor() {
    this.blockQueue = [];
    for (let i = 0; i < QUEUE_SIZE; i++) {
      this.blockQueue.push(new Tetromino(0));
    }
  }

  cycle(): Tetromino {
    this.blockQueue.push(new Tetromino(0));
    return this.blockQueue.shift();
  }

  getBlock(index: number): Tetromino {
    return this.blockQueue[index];
  }
}
