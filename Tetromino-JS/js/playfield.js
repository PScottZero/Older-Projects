/**
 * @author Paul Scott
 * @version 20 March 2019
 *
 * this class represents the tetris playfield
 * and handles most game play features
 */
class PlayField {

  /**
   * playfield constructor
   */
  constructor() {

    // instance data
    this.blockSize = 30;
    this.pf_width = 300;
    this.pf_height = 600;

    // static variables
    PlayField.currBlock = new Tetromino(0);
    PlayField.score = 0;
    PlayField.currR = 0;
    PlayField.currC = 3;
    PlayField.isRunning = true;
    PlayField.isPaused = false;
    PlayField.newBlock = true;

    // generate playfield of size 10x20
    PlayField.grid = new Array(20);
    for (let i = 0; i < PlayField.grid.length; i++)
      PlayField.grid[i] = new Array(10).fill(0);

    // generate next block array
    PlayField.blocks = [];
    for (let i = 0; i < 5; i++)
      PlayField.blocks.push(new Tetromino(Math.floor(Math.random() * 7) + 1));

    this.colors = [
        "#00000000",
        "#acfff5",
        "#35a8d0",
        "#d09959",
        "#faff7a",
        "#7cd293",
        "#ab68ff",
        "#ff5366"
    ];
  }

  /**
   * draw playfield and current block
   */
  draw() {
    let canvas = document.getElementById("playfield");
    let ctx = canvas.getContext("2d");

    // clear canvas
    ctx.clearRect(0, 0, this.pf_width, this.pf_height);
    ctx.beginPath();

    // draw previous blocks on playfield grid
    for (let r = 0; r < PlayField.grid.length; r++) {
      for (let c = 0; c < PlayField.grid[r].length; c++) {
        ctx.fillStyle = this.colors[PlayField.grid[r][c]];
        ctx.fillRect(c * this.blockSize + 1, r * this.blockSize + 1,
            this.blockSize - 2, this.blockSize - 2);
      }
    }

    // draws current block
    PlayField.currBlock.draw(ctx, PlayField.currR, PlayField.currC, this.blockSize, this.colors);

    // font styling
    ctx.fillStyle = "#FFFFFF";
    ctx.font = "48px Arial";

    // draws game over on playfield
    if (!PlayField.isRunning)
      ctx.fillText("Game Over", 30, 300);

    // draws pause on playfield
    else if (PlayField.isRunning && PlayField.isPaused)
      ctx.fillText("Pause", 85, 300);
  }

  /**
   * draws next five blocks onto next block panel
   */
  drawNext() {
    let canvas = document.getElementById("next_block");
    let ctx = canvas.getContext("2d");

    // clear canvas
    ctx.clearRect(0, 0, 150, 475);
    ctx.beginPath();

    // draw blocks onto panel
    for (let i = 0; i < 5; i++) {
      let offsetR = 2.5;
      let offsetC = 1.5;
      let shape = PlayField.blocks[i];

      // drawing offsets for specific blocks
      if (shape.getShape() === 1 || shape.getShape() === 4) offsetC = 1; // column offset for i-block and o-block
      if (shape.getShape() === 1) offsetR = 1; // row offset for i-block

      // draw block onto next block panel
      PlayField.blocks[i].draw(ctx, i * 3 + offsetR, offsetC, 25, this.colors);
    }
  }

  /**
   * check if tetris block has not moved
   * due to collision. lines may be cleared,
   * and a new block will be generated
   * @param oldR - previous row of block
   */
  static checkDown(oldR) {
    if (oldR === PlayField.currR) {
      if (PlayField.currR === 0) PlayField.isRunning = false;
      PlayField.newBlock = true;
      PlayField.currBlock.merge();
      PlayField.clearLines();
      PlayField.currR = 0;
      PlayField.currC = 3;
    }
  }

  /**
   * clear tetris lines if a line is full
   */
  static clearLines() {
    let count = 0;
    for (let r = 0; r < PlayField.grid.length; r++) {

      // check if line is cleared, then remove line
      if (!PlayField.grid[r].includes(0)) {
        for (let i = r; i > 0; i--)
          PlayField.grid[i] = PlayField.grid[i - 1];
        PlayField.grid[0] = new Array(10).fill(0);

        // add 10 points to score if line cleared
        count += 10;
      }
    }

    // score multiplier based on how many lines were cleared simultaneously
    if (count === 2) count *= 2;
    else if (count === 3) count *= 3;
    else if (count === 4) count *= 4;
    PlayField.score += count;

    // update score label
    document.getElementById("score").innerHTML = "<p> Score: </p><p>" + PlayField.formatScore() + "</p>"
  }

  /**
   * format score into six digit score with leading zeros
   * @returns {string} - six digit long score
   */
  static formatScore() {
    let scoreString = PlayField.score.toString();
    let size = 6 - scoreString.length;
    for (let i = 0; i < size; i++)
      scoreString = "0" + scoreString;
    return scoreString;
  }

  /**
   * pause game
   */
  static pause() {
    PlayField.isPaused = !PlayField.isPaused;
  }

  /**
   * resets tetris game
   */
  static async restart() {

    // stop current game
    PlayField.isRunning = false;
    await new Promise(resolve => setTimeout(resolve, 100));

    // reset parameters
    PlayField.currBlock = new Tetromino(0);
    PlayField.isRunning = true;
    PlayField.isPaused = false;
    PlayField.newBlock = true;
    PlayField.currC = 3;
    PlayField.currR = 0;
    PlayField.score = 0;

    // generate new block array
    PlayField.blocks = [];
    for (let i = 0; i < 5; i++)
      PlayField.blocks.push(new Tetromino(Math.floor(Math.random() * 7) + 1));

    // reset score label
    document.getElementById("score").innerHTML = "<p> Score: </p><p> 000000 </p>";

    // reset playfield
    PlayField.grid = new Array(20);
    for (let i = 0; i < PlayField.grid.length; i++)
      PlayField.grid[i] = new Array(10).fill(0);
  }

  /**
   * tetris game loop
   */
  gameLoop() {
    let self = this;
    let i = 0; // counter
    let speed = 60; // speed control
    self.drawNext();
    requestAnimationFrame(function step() {

      // only runs if game is not over
      if (PlayField.isRunning) {

        // only runs if game is not paused
        if (!PlayField.isPaused) {

          // change speed depending on score
          if (PlayField.score >= 5000) speed = 2;
          else if (PlayField.score >= 1000) speed = 4;
          else if (PlayField.score >= 750) speed = 8;
          else if (PlayField.score >= 500) speed = 15;
          else if (PlayField.score >= 250) speed = 30;

          // generate new block
          if (PlayField.newBlock) {
            PlayField.currBlock = PlayField.blocks.shift();
            PlayField.blocks.push(new Tetromino(Math.floor(Math.random() * 7) + 1));
            PlayField.newBlock = false;
          }

          // move blocks down at specified speed
          if (i % speed === speed - 1) {
            let oldR = PlayField.currR;
            PlayField.currBlock.down();
            PlayField.checkDown(oldR);
          } i++;
        }

        // update playfield
        self.draw();
        self.drawNext();
        requestAnimationFrame(step)
      }
      self.draw();
    });
  }
}