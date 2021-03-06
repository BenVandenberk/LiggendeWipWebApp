package be.oklw.filter;

import be.oklw.model.Account;
import be.oklw.model.PermissieNiveau;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by java on 07.12.15.
 */
public class AuthorizationFilter implements Filter {
    private HashMap<String, ArrayList<String>> toegelatenUrlsPerRol;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        toegelatenUrlsPerRol = new HashMap<>();

        ArrayList<String> bezoekerUrls = new ArrayList<>();
        bezoekerUrls.add("/login.xhtml");
        bezoekerUrls.add("/home.xhtml");
        bezoekerUrls.add("/registreren.xhtml");
        bezoekerUrls.add("/bezoeker_fotos.xhtml");
        bezoekerUrls.add("/bezoeker_kampioenschap_fotos.xhtml");
        bezoekerUrls.add("/bezoeker_kampioenschapsindex.xhtml");
        bezoekerUrls.add("/bezoeker_kampioenschapspagina.xhtml");
        bezoekerUrls.add("/bezoeker_toernooipagina.xhtml");
        bezoekerUrls.add("/paswoord_vergeten.xhtml");
        bezoekerUrls.add("/bezoeker_nieuws.xhtml");
        bezoekerUrls.add("/paswoord_reset.xhtml");

        ArrayList<String> clubUrls = new ArrayList<>();
        clubUrls.add("/login.xhtml");
        clubUrls.add("/club_beheer.xhtml");
        clubUrls.add("/club_accountbeheer.xhtml");
        clubUrls.add("/club_contactbeheer.xhtml");
        clubUrls.add("/club_schieting.xhtml");
        clubUrls.add("/club_inschrijven.xhtml");
        clubUrls.add("/club_inschrijvingen_beheren.xhtml");
        clubUrls.add("/club_kampioenschapspagina.xhtml");
        clubUrls.add("/club_nieuw_toernooi.xhtml");
        clubUrls.add("/club_toernooi_aanpassen.xhtml");
        clubUrls.add("/club_sponsors.xhtml");
        clubUrls.add("/club_inschrijving.xhtml");
        clubUrls.add("/club_nieuwe_sponsor.xhtml");
        clubUrls.add("/club_sponsor_aanpassen.xhtml");
        clubUrls.add("/club_fotos_uploaden.xhtml");
        clubUrls.add("/home.xhtml");
        clubUrls.add("/bezoeker_fotos.xhtml");
        clubUrls.add("/bezoeker_kampioenschap_fotos.xhtml");
        clubUrls.add("/bezoeker_kampioenschapsindex.xhtml");
        clubUrls.add("/bezoeker_kampioenschapspagina.xhtml");
        clubUrls.add("/bezoeker_toernooipagina.xhtml");
        clubUrls.add("/bezoeker_nieuws.xhtml");
        clubUrls.add("/paswoord_reset.xhtml");

        ArrayList<String> systeemUrls = new ArrayList<>();
        systeemUrls.add("/login.xhtml");
        systeemUrls.add("/systeem_clubbeheer.xhtml");
        systeemUrls.add("/systeem_nieuwe_club.xhtml");
        systeemUrls.add("/systeem_accountbeheer.xhtml");
        systeemUrls.add("/systeem_nieuw_contact.xhtml");
        systeemUrls.add("/systeem_nieuw_kampioenschap.xhtml");
        systeemUrls.add("/systeem_site_sponsoring.xhtml");
        systeemUrls.add("/systeem_sponsor.xhtml");
        systeemUrls.add("/home.xhtml");
        systeemUrls.add("/bezoeker_fotos.xhtml");
        systeemUrls.add("/bezoeker_kampioenschap_fotos.xhtml");
        systeemUrls.add("/bezoeker_kampioenschapsindex.xhtml");
        systeemUrls.add("/bezoeker_kampioenschapspagina.xhtml");
        systeemUrls.add("/bezoeker_toernooipagina.xhtml");
        systeemUrls.add("/bezoeker_nieuws.xhtml");
        systeemUrls.add("/paswoord_reset.xhtml");
        systeemUrls.add("/systeem_layout.xhtml");

        ArrayList<String> lidUrls = new ArrayList<>();
        lidUrls.add("/login.xhtml");
        lidUrls.add("/home.xhtml");
        lidUrls.add("/bezoeker_fotos.xhtml");
        lidUrls.add("/bezoeker_kampioenschap_fotos.xhtml");
        lidUrls.add("/bezoeker_kampioenschapsindex.xhtml");
        lidUrls.add("/bezoeker_kampioenschapspagina.xhtml");
        lidUrls.add("/bezoeker_toernooipagina.xhtml");
        lidUrls.add("/lid_accountbeheer.xhtml");
        lidUrls.add("/lid_inschrijvingen.xhtml");
        lidUrls.add("/bezoeker_nieuws.xhtml");
        lidUrls.add("/paswoord_reset.xhtml");

        toegelatenUrlsPerRol.put("club", clubUrls);
        toegelatenUrlsPerRol.put("bezoeker", bezoekerUrls);
        toegelatenUrlsPerRol.put("systeem", systeemUrls);
        toegelatenUrlsPerRol.put("lid", lidUrls);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        PermissieNiveau permissieNiveau;

        if (session == null) {
            permissieNiveau = PermissieNiveau.BEZOEKER;
        } else {
            Account account = (Account) session.getAttribute("user");

            if (account == null) {
                permissieNiveau = PermissieNiveau.BEZOEKER;
            } else {
                permissieNiveau = account.getPermissieNiveau();
            }
        }

        List<String> toegelatenUrls = toegelatenUrlsPerRol.get(permissieNiveau.toString());
        String teFilterenUrl = request.getServletPath();
        boolean ignore = teFilterenUrl.startsWith("/javax") || teFilterenUrl.startsWith("/home");

        System.out.println(String.format("%s %s", ignore ? "IGNORING" : "FILTERING", request.getServletPath()));

        if (!ignore) {
            if (toegelatenUrls.contains(teFilterenUrl)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                String message = String.format("Toegang tot %s niet toegelaten als %s", teFilterenUrl.substring(1), permissieNiveau.toString());
                session = request.getSession(true);
                session.setAttribute("redirectMessage", message);
                response.sendRedirect("/login.xhtml");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
