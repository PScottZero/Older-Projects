import {Component} from '@angular/core';
import {Drawing} from '../../objects/Drawing';
import {DrawingService} from '../../services/drawing.service';

@Component({
  selector: 'app-drawings',
  templateUrl: './drawings.component.html',
  styleUrls: ['./drawings.component.scss']
})
export class DrawingsComponent {

  constructor(private drawingService: DrawingService) { }

  // gets drawings list
  getDrawings(): Drawing[] {
    return this.drawingService.drawings;
  }

  // selects drawing to be enlarged
  selectDrawing(drawing: Drawing) {
    this.drawingService.selectedDrawing = drawing;
  }
}
