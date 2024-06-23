import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangePWDComponent } from './change-pwd.component';

describe('ChangePWDComponent', () => {
  let component: ChangePWDComponent;
  let fixture: ComponentFixture<ChangePWDComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangePWDComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangePWDComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
