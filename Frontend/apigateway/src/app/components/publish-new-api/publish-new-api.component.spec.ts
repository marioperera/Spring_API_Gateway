import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublishNewApiComponent } from './publish-new-api.component';

describe('PublishNewApiComponent', () => {
  let component: PublishNewApiComponent;
  let fixture: ComponentFixture<PublishNewApiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublishNewApiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PublishNewApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
