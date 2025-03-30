package org.example.locket_clone_backend.mapper;

public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}
