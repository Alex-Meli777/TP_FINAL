package com.poo2.tpfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<OrganisationJpa, OrganisationJpa.OrganisationId> {

    @Query("SELECT o FROM OrganisationJpa o WHERE o.organisateur.id_par = :organisateurId")
    List<OrganisationJpa> findByOrganisateurId(@Param("organisateurId") Long organisateurId);

    @Query("SELECT o FROM OrganisationJpa o WHERE o.evenement.id_ev = :evenementId")
    List<OrganisationJpa> findByEvenementId(@Param("evenementId") Long evenementId);

    @Query("SELECT o FROM OrganisationJpa o WHERE o.organisateur.id_par = :organisateurId AND o.evenement.id_ev = :evenementId")
    Optional<OrganisationJpa> findByOrganisateurIdAndEvenementId(
            @Param("organisateurId") Long organisateurId,
            @Param("evenementId") Long evenementId);

    @Query("SELECT o FROM OrganisationJpa o WHERE o.organisateur.id_par = :organisateurId AND o.createur = true")
    List<OrganisationJpa> findCreatedEventsByOrganisateurId(@Param("organisateurId") Long organisateurId);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM OrganisationJpa o WHERE o.organisateur.id_par = :organisateurId AND o.evenement.id_ev = :evenementId")
    boolean existsByOrganisateurIdAndEvenementId(
            @Param("organisateurId") Long organisateurId,
            @Param("evenementId") Long evenementId);
}