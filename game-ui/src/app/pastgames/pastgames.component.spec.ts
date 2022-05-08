import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PastgamesComponent } from './pastgames.component';

describe('PastgamesComponent', () => {
  let component: PastgamesComponent;
  let fixture: ComponentFixture<PastgamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PastgamesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PastgamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
