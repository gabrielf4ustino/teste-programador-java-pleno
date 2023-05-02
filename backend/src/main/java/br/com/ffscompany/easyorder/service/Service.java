package br.com.ffscompany.easyorder.service;

import java.util.List;
import java.util.Optional;

public interface Service<T, U> {

    T register(U u);

    Optional<T> edit(U u);

    void remove(Long id);

    List<T> findAll();

    Optional<T> findById(Long id);
}
