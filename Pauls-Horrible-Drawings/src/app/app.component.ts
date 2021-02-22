import { Component } from '@angular/core';
import {DrawingService} from './services/drawing.service';
import {Drawing} from './objects/Drawing';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private drawingService: DrawingService) {}

  // checks if drawing has been selected
  drawingIsSelected(): boolean {
    return this.getSelectedDrawing() !== undefined;
  }

  // returns currently selected drawing
  getSelectedDrawing(): Drawing {
    return this.drawingService.selectedDrawing;
  }
}
