package uk.co.bartcode.service.post;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(DatePostCount.Key.class)
public class DatePostCount {

    @Id
    @Column(name = "year", nullable = false, updatable = false)
    private int year;
    @Id
    @Column(name = "month", nullable = false, updatable = false)
    private int month;
    @Column(name = "count", nullable = false, updatable = false)
    private long count;

    DatePostCount() {
    }

    public DatePostCount(int year, int month, long count) {
        this.year = year;
        this.month = month;
        this.count = count;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("year", year)
                .add("month", month)
                .add("count", count)
                .toString();
    }

    static class Key implements Serializable {
        private int year;
        private int month;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return year == key.year && month == key.month;
        }

        @Override
        public int hashCode() {
            return Objects.hash(year, month);
        }
    }

}
