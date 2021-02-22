import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { PlayfieldComponent } from './components/playfield/playfield.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { NextBlockComponent } from './components/next-block/next-block.component';

@NgModule({
  declarations: [
    AppComponent,
    PlayfieldComponent,
    SidebarComponent,
    NextBlockComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
