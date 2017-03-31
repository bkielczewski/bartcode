import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DatePostCountComponent } from './date-post-count.component';

describe('DatePostCountComponent', () => {
  let component: DatePostCountComponent;
  let fixture: ComponentFixture<DatePostCountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DatePostCountComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatePostCountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
