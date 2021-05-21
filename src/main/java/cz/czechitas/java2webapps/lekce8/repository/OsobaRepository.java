package cz.czechitas.java2webapps.lekce8.repository;

import cz.czechitas.java2webapps.lekce8.entity.Osoba;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository = podivej se na tuhle tridu, vytvor jeji instanci pri startu
 *             = pres Constructor a Autowired ji doplnim a mohu pouzit kdekoli v aplikaci
 *             = implementaci vytvori samotny String
 *
 * extends = dedi z jineho interface
 * CrudRepository = create-read-update-delete
 * <Osoba, Long> = entita typu Osoba, uvedeny typ id osoby
 *
 * interface = neni v nem vykonny kod, uvadi metody, parametry metody, navratove typy
 */

    @Repository
    public interface OsobaRepository extends CrudRepository<Osoba, Long> {
    }



