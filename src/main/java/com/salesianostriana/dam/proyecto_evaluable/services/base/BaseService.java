package com.salesianostriana.dam.proyecto_evaluable.services.base;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BaseService <T, ID> {


    public List<T> findAll();

    public Optional<T> findById(ID id);

    public T save(T entity);

    public T edit(T entity);

    public void deleteById(ID id);

    public void delete(T entity);


}
