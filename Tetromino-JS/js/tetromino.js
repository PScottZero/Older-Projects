/**
 * @author Paul Scott
 * @version 12 March 2019
 *
 * class representing tetromino block
 */
class Tetromino {

  /**
   * initialize block
   * @param shape - number corresponding to shape
   */
  constructor(shape) {

    this.grid = null;
    this.shape = shape;

    switch (shape) {

      // i-block
      case 1:
        this.grid = [
            [0, 0, 0, 0],
            [0, 0, 0, 0],
            [1, 1, 1, 1],
            [0, 0, 0, 0]
        ]; break;

      // j-block
      case 2:
        this.grid = [
            [2, 0, 0],
            [2, 2, 2],
            [0, 0, 0]
        ]; break;

      // l-block
      case 3:
        this.grid = [
            [0, 0, 3],
            [3, 3, 3],
            [0, 0, 0]
        ]; break;

      // o-block
      case 4:
        this.grid = [
            [0, 4, 4, 0],
            [0, 4, 4, 0],
            [0, 0, 0, 0]
        ]; break;

      // s-block
      case 5:
        this.grid = [
            [0, 5, 5],
            [5, 5, 0],
            [0, 0, 0]
        ]; break;

      // t-block
      case 6:
        this.grid = [
            [0, 6, 0],
            [6, 6, 6],
            [0, 0, 0]
        ]; break;

      // z-block
      case 7:
        this.grid = [
            [7, 7, 0],
            [0, 7, 7],
            [0, 0, 0]
        ]; break;
    }
  }

  /**
   * transpose block matrix
   */
  transpose() {
    for (let r = 0; r < this.grid.length; r++) {
      for (let c = r; c < this.grid[r].length; c++) {
        let temp = this.grid[r][c];
        this.grid[r][c] = this.grid[c][r];
        this.grid[c][r] = temp;
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
          let temp = this.grid[r][c];
          this.grid[r][c] = this.grid[r][this.grid.length - c - 1];
          this.grid[r][this.grid.length - c - 1] = temp;
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
      for(let r = 0; r < this.grid.length / 2; r++) {
        for (let c = 0; c < this.grid[r].length; c++) {
          let temp = this.grid[r][c];
          this.grid[r][c] = this.grid[this.grid.length - r - 1][c];
          this.grid[this.grid.length - r - 1][c] = temp;
        }
      }
    }
  }

  /**
   * move block left
   */
  left() {
    PlayField.currC--;
    if (this.collision()) PlayField.currC++;
  }

  /**
   * move block right
   */
  right() {
    PlayField.currC++;
    if (this.collision()) PlayField.currC--;
  }

  /**
   * move block down
   */
  down() {
    PlayField.currR++;
    if (this.collision()) PlayField.currR--;
  }

  /**
   * rotate block clockwise
   */
  clockwise() {
    this.rotateCW();
    if (this.collision()) this.rotateCCW();
  }

  /**
   * rotate block counter clockwise
   */
  counterClockwise() {
    this.rotateCCW();
    if (this.collision()) this.rotateCW();
  }

  /**
   * check for block collision with playfield
   * @returns {boolean} - true if collision occurs, false otherwise
   */
  collision() {
    for (let r = 0; r < this.grid.length; r++) {
      for (let c = 0; c < this.grid[r].length; c++) {
        if (this.grid[r][c] !== 0) {

          // check out of bounds
          if (PlayField.currC + c < 0 ||
              PlayField.currC + c >= PlayField.grid[0].length ||
              PlayField.currR + r >= PlayField.grid.length)
            return true;

          // check block collision
          if (PlayField.grid[PlayField.currR + r][PlayField.currC + c] !== 0) return true;
        }
      }
    }
    return false;
  }

  /**
   * merge block with playfield
   */
  merge() {
    for (let r = 0; r < this.grid.length; r++) {
      for (let c = 0; c < this.grid[r].length; c++) {
        if (this.grid[r][c] !== 0) {
          PlayField.grid[PlayField.currR + r][PlayField.currC + c] = this.grid[r][c];
        }
      }
    }
  }

  /**
   * returns shape number
   * @returns {*} - shape number
   */
  getShape() { return this.shape; }

  /**
   * draws block on playfield
   * @param context - playfield canvas context
   * @param row - playfield row
   * @param col - playfield column
   * @param size - block size
   * @param colors - block colors
   */
  draw(context, row, col, size, colors) {
    context.fillStyle = colors[this.shape];
    for (let r = 0; r < this.grid.length; r++) {
      for (let c = 0; c < this.grid[r].length; c++) {
        if (this.grid[r][c] !== 0) {
          let x = (col + c) * size + 1;
          let y = (row + r) * size + 1;
          context.fillRect(x, y, size - 2, size - 2)
        }
      }
    }
  }
}