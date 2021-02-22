import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NextBlockComponent } from './next-block.component';

describe('NextBlockComponent', () => {
  let component: NextBlockComponent;
  let fixture: ComponentFixture<NextBlockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NextBlockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NextBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
