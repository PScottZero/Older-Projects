import { Injectable } from '@angular/core';
import {Drawing} from '../objects/Drawing';

const ART_DIR = 'assets/img/art/';

@Injectable({
  providedIn: 'root'
})
export class DrawingService {

  // selected drawing
  selectedDrawing: Drawing;

  // drawings list
  drawings = [
    new Drawing(ART_DIR + 'raspi.jpg', 'Raspberry Pi'),
    new Drawing(ART_DIR + 'kida_polarbear.jpg', 'Radiohead: Kid A Polarbear'),
    new Drawing(ART_DIR + 'cyriak_meow.jpg', 'Cyriak: MEOW Zombie Cat'),
    new Drawing(ART_DIR + 'modified_bear.jpg', 'Radiohead: Modified Bear'),
    new Drawing(ART_DIR + 'might_be_wrong.jpg', 'Radiohead: I Might Be Wrong'),
    new Drawing(ART_DIR + 'kirby_griffin.jpg', 'Kirby Griffin (Cursed Image)'),
    new Drawing(ART_DIR + 'zombie_cat_rock.jpg', 'Zombie Cat Rock'),
    new Drawing(ART_DIR + 'zombie_cat_rock_alt.jpg', 'Zombie Cat Rock (Alt)'),
    new Drawing(ART_DIR + 'aphex.jpg', 'Aphex Twin: Selected Ambient Works 85-92'),
    new Drawing(ART_DIR + 'flavortown.jpg', 'I\'m Taking You To Flavortown'),
    new Drawing(ART_DIR + 'martin.jpg', 'C. F. Martin D-16GT'),
    new Drawing(ART_DIR + 'congratulations.jpg', 'MGMT: Congratulations'),
    new Drawing(ART_DIR + 'bfr.jpg', 'SpaceX BFR'),
    new Drawing(ART_DIR + 'beatles.jpg', 'The Beatles'),
    new Drawing(ART_DIR + 'division_bell.jpg', 'Pink Floyd: The Division Bell'),
    new Drawing(ART_DIR + 'cessna.jpg', 'Cessna Logo'),
    new Drawing(ART_DIR + 'grievous.jpg', 'General Grievous'),
    new Drawing(ART_DIR + 'grievous_marker.jpg', 'General Grievous (Whiteboard)'),
    new Drawing(ART_DIR + 'rick_and_morty.jpg', 'Rick and Morty'),
    new Drawing(ART_DIR + 'meow.jpg', 'Space Dandy: Meow'),
    new Drawing(ART_DIR + 'zombie_cat_2.jpg', 'Zombie Cat #2'),
    new Drawing(ART_DIR + 'demon_cat.jpg', 'Demon Cat'),
    new Drawing(ART_DIR + 'zombie_cat_1.jpg', 'Zombie Cat #1'),
  ];
}
