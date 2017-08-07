import { Component, OnInit } from '@angular/core';
import { DatePostCountService } from '../shared/date-post-count.service';
import { DatePostCount } from '../shared/date-post-count';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-date-post-count',
  templateUrl: './date-post-count.component.html',
  styleUrls: ['./date-post-count.component.scss']
})
export class DatePostCountComponent implements OnInit {

  years: number[] = [];
  yearCounts: { [year: number]: DatePostCount[] } = {};

  constructor(private dateCountService: DatePostCountService) {
  }

  ngOnInit() {
    this.dateCountService.getDateCounts()
      .catch(() => <Observable<DatePostCount>>Observable.empty())
      .subscribe(dateCount => this.update(dateCount));
  }

  private update(dateCount: DatePostCount) {
    this.updateYears(dateCount);
    this.updateYearCounts(dateCount);
  }

  private updateYears(dateCount) {
    if (this.years.indexOf(dateCount.year) == -1) {
      this.years.push(dateCount.year);
    }
  }

  private updateYearCounts(dateCount) {
    if (!this.yearCounts[dateCount.year]) {
      this.yearCounts[dateCount.year] = [dateCount];
    } else {
      this.yearCounts[dateCount.year].push(dateCount);
    }
  }

}
