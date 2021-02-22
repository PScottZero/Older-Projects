import {BlockType} from '../enum/BlockType';

export class Tetromino {

  constructor(type: BlockType) {

    this.grid = null;
    this.type = type;

    if (type === 0) {
      type = Math.floor(Math.random() * 7) + 1;
    }

    switch (type) {

      case BlockType.I_BLOCK:
        this.grid = [
          [0, 0, 0, 0],
          [0, 0, 0, 0],
          [1, 1, 1, 1],
          [0, 0, 0, 0]
        ];
        break;

      case BlockType.J_BLOCK:
        this.grid = [
          [2, 0, 0],
          [2, 2, 2],
          [0, 0, 0]
        ];
        break;

      case BlockType.L_BLOCK:
        this.grid = [
          [0, 0, 3],
          [3, 3, 3],
          [0, 0, 0]
        ];
        break;

      case BlockType.O_BLOCK:
        this.grid = [
          [0, 4, 4, 0],
          [0, 4, 4, 0],
          [0, 0, 0, 0]
        ];
        break;

      case BlockType.S_BLOCK:
        this.grid = [
          [0, 5, 5],
          [5, 5, 0],
          [0, 0, 0]
        ];
        break;

      case BlockType.T_BLOCK:
        this.grid = [
          [0, 6, 0],
          [6, 6, 6],
          [0, 0, 0]
        ];
        break;

      case BlockType.Z_BLOCK:
        this.grid = [
          [7, 7, 0],
          [0, 7, 7],
          [0, 0, 0]
        ];
        break;
    }
  }

  static BLOCK_COLORS = [
    '#00000000', // transparent
    '#acfff5',
    '#35a8d0',
    '#d09959',
    '#faff7a',
    '#7cd293',
    '#ab68ff',
    '#ff5366'
  ];

  grid: number[][];
  type: BlockType;

  /**
   * transpose block matrix
   */
  transpose() {
    for (let r = 0; r < this.grid.length; r++) {
      for (let c = r; c < this.grid[r].length; c++) {
        this.swap(r, c, c, r);
      }
    }
  }

  /**
   * rotate block matrix clockwise
   */
  rotateCW() {
    if (this.grid.length === this.grid[0].length) {

      // transpose matrix
      this.transpose();

      // flip matrix horizontally
      for (let c = 0; c < this.grid[0].length / 2; c++) {
        for (let r = 0; r < this.grid.length; r++) {
          this.swap(r, c, r, this.grid.length - c - 1);
        }
      }
    }
  }

  /**
   * rotate block matrix counter clockwise
   */
  rotateCCW() {
    if (this.grid.length === this.grid[0].length) {

      // transpose
      this.transpose();

      // flip matrix vertically
      for (let r = 0; r < this.grid.length / 2; r++) {
        for (let c = 0; c < this.grid[r].length; c++) {
          this.swap(r, c, this.grid.length - r - 1, c);
        }
      }
    }
  }

  /**
   * swaps grid[r1][c1] with grid[r2][c2]
   * @param r1 row of first entry
   * @param c1 column of first entry
   * @param r2 row of second entry
   * @param c2 column of second entry
   */
  private swap(r1: number, c1: number, r2: number, c2: number) {
    const temp = this.grid[r1][c1];
    this.grid[r1][c1] = this.grid[r2][c2];
    this.grid[r2][c2] = temp;
  }

  draw(context, row, col, size, colors) {
    if (this.grid) {
      context.strokeStyle = colors[this.type];
      context.lineWidth = '2';
      for (let r = 0; r < this.grid.length; r++) {
        for (let c = 0; c < this.grid[r].length; c++) {
          if (this.grid[r][c] !== 0) {
            const x = (col + c) * size + 2;
            const y = (row + r) * size + 2;
            context.rect(x, y, size - 4, size - 4);
            context.stroke();
          }
        }
      }
    }
  }
}
