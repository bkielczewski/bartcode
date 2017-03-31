import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { TagPostCountComponent } from './tag-post-count.component';

describe('TagPostCountComponent', () => {
  let component: TagPostCountComponent;
  let fixture: ComponentFixture<TagPostCountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TagPostCountComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TagPostCountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
