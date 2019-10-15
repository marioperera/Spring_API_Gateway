import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatenewapiComponent } from './createnewapi.component';

describe('CreatenewapiComponent', () => {
  let component: CreatenewapiComponent;
  let fixture: ComponentFixture<CreatenewapiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatenewapiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatenewapiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
