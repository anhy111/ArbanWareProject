package com.aw.arbanware.domain.review;

import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class RecommendationKey implements Serializable {
    private Review review;
    private Member member;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RecommendationKey that = (RecommendationKey) o;
        return Objects.equals(review, that.review) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(review, member);
    }
}
