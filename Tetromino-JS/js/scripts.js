/**
 * @author Paul Scott
 * @version 25 March 2019
 *
 * main javascript file
 */
$(document).ready(function() {

  // properly scale playfield canvas
  let canvas = document.getElementById("playfield");
  canvas.width = 600;
  canvas.height = 1200;
  canvas.style.width = "300px";
  canvas.style.height = "600px";
  canvas.getContext("2d").scale(2, 2);

  // properly scale next block canvas
  let next = document.getElementById("next_block");
  next.width = 300;
  next.height = 950;
  next.style.width = "150px";
  next.style.height = "475px";
  next.getContext("2d").scale(2, 2);

  // initialize playfield
  let playfield = new PlayField();
  let ctx = canvas.getContext('2d');
  ctx.fillStyle = "#FFFFFF";
  ctx.font = "48px Arial";

  // draws game over on playfield
  ctx.fillText("Press 'N'", 60, 275);
  ctx.fillText("To Start", 65, 325);

  // pause and new game handler
  document.onkeypress = async function (e) {
    if (e.key === 'n') {
      await PlayField.restart();
      playfield.gameLoop();
    } else if (e.key === 'p') {
      PlayField.pause();
    }
  };

  // controls
  document.onkeydown = function(e) {
    if (!PlayField.isPaused) {
      switch (e.key) {

        // down
        case 'ArrowDown':
          let oldR = PlayField.currR;
          PlayField.currBlock.down();
          PlayField.checkDown(oldR);
          break;

        // left
        case 'ArrowLeft':
          PlayField.currBlock.left();
          break;

        // right
        case 'ArrowRight':
          PlayField.currBlock.right();
          break;

        // clockwise
        case 'x':
        case ' ':
          PlayField.currBlock.clockwise();
          break;

        // counter clockwise
        case 'z':
          PlayField.currBlock.counterClockwise();
          break;
      }
    }
  };
});

function nav(component) {
  let about = document.getElementById("about");
  let controls = document.getElementById("controls");
  if (component.id === "nav_about") {
    if (about.style.display === "none") {
      about.style.display = "block";
    } else {
      about.style.display = "none";
    }
    controls.style.display = "none";
  }
  else {
    if (controls.style.display === "none") {
      controls.style.display = "block";
    } else {
      controls.style.display = "none";
    }
    about.style.display = "none";
  }
}

/**
 * hides controls and about divs if mouse is clicked off
 */
$(document).mouseup(function() {
    let controls = document.getElementById("controls");
    let about = document.getElementById("about");
    controls.style.display = 'none';
    about.style.display = 'none';
});