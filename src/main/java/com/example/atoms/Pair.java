package com.example.atoms;

import jakarta.annotation.Nullable;

public class Pair<L, R> {

    @Nullable
    private final L left;

    @Nullable
    private final R right;

    private Pair(@Nullable L left, @Nullable R right) {
        this.left = left;
        this.right = right;
    }

    @Nullable
    public L getLeft() {
        return left;
    }

    @Nullable
    public R getRight() {
        return right;
    }

    public static <L, R> Pair<L, R> pair(@Nullable L left, @Nullable R right) {
        return new Pair<>(left, right);
    }

}
