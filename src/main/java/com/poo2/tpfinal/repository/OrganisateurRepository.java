package com.poo2.tpfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisateurRepository extends JpaRepository<OrganisateurJpa, Long> {
    /**
     * Trouve un organisateur par son nom d'utilisateur
     * @param nomUtilisateur le nom d'utilisateur à rechercher
     * @return l'organisateur correspondant s'il existe, null sinon
     */
    @Query("SELECT o FROM OrganisateurJpa o WHERE o.nom_utilisateur = :nomUtilisateur")
    OrganisateurJpa findByNomUtilisateur(@Param("nomUtilisateur") String nomUtilisateur);
}
