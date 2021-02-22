import {Component, Input} from '@angular/core';
import {Drawing} from '../../objects/Drawing';
import {DrawingService} from '../../services/drawing.service';

@Component({
  selector: 'app-overlay',
  templateUrl: './overlay.component.html',
  styleUrls: ['./overlay.component.scss']
})
export class OverlayComponent {
  @Input() drawing: Drawing;

  constructor(private drawingService: DrawingService) {}

  // sets current drawing to undefined and hides overlay
  unselectDrawing() {
    this.drawingService.selectedDrawing = undefined;
  }
}
