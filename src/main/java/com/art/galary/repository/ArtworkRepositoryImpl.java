package com.art.galary.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.art.galary.models.Artwork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArtworkRepositoryImpl implements ArtworkRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Autowired
    public ArtworkRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    private RowMapper<Artwork> artworkRowMapper = new RowMapper<Artwork>() {
        public Artwork mapRow(ResultSet rs, int rowNum) throws SQLException {
            Artwork artwork = new Artwork();
            artwork.setId(rs.getInt("id"));
            artwork.setTitle(rs.getString("title"));
            artwork.setDescription(rs.getString("description"));
            artwork.setCategory(rs.getString("category"));
            artwork.setLabel(rs.getString("label"));
            artwork.setPrice(rs.getInt("price"));
            artwork.setLikes(rs.getInt("likes"));
            artwork.setOwner_id(rs.getInt("owner_id"));
            artwork.setImgUrl(rs.getString("imgUrl"));
            return artwork;
        }
    };

    @Override
    public List<Artwork> getArtworks() {
        String query = "SELECT * FROM artwork";
        return jdbcTemplate.query(query, artworkRowMapper);
    }

    @Override
    public List<Artwork> findAllArtworks() {
        return null;
    }
    
    public void deleteById(int id) {
        String sql = "DELETE FROM artwork WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void save(Artwork artwork) {
        String query = "INSERT INTO artwork(title, description, category, label, price, likes, imgUrl, owner_id) " +
                        "VALUES(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(query, artwork.getTitle(), artwork.getDescription(), artwork.getCategory(), artwork.getLabel(),
                artwork.getPrice(), artwork.getLikes(), artwork.getImgUrl(), artwork.getOwner_id());
    }

    @Override
    public Artwork findArtworkById(int id) {
        String query = "SELECT * FROM artwork WHERE id = ?";
        return jdbcTemplate.queryForObject(query, artworkRowMapper, id);
    }

    @Override
    public List<Artwork> findArtworkByOwner(int id) {
        String query = "SELECT * FROM artwork WHERE owner_id = ?";
        return jdbcTemplate.query(query, artworkRowMapper, id);
    }

 // Method to update artwork (excluding image URL)
    public void updateArtwork(Artwork artwork) {
        String query = "UPDATE artwork SET title = ?, description = ?, category = ?, label = ?, price = ?, likes = ?, owner_id = ? WHERE id = ?";
        jdbcTemplate.update(query, artwork.getTitle(), artwork.getDescription(), artwork.getCategory(),
                artwork.getLabel(), artwork.getPrice(), artwork.getLikes(), artwork.getOwner_id(), artwork.getId());
    }

    @Override
    public void updateArtworkLikes(int id, int likes) {
        likes = likes + 1;
        String query = "UPDATE artwork SET likes = ? WHERE id = ?";
        jdbcTemplate.update(query, likes, id);
    }

    @Override
    public void acceptArt(int id) {
        String query = "UPDATE artwork SET label = 'Unsold' WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public void declineArt(int id) {
        String query = "DELETE FROM artwork WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public List<Artwork> getArtworksByCategory(String category) {
        String query = "SELECT * FROM artwork WHERE category = ?";
        return jdbcTemplate.query(query, artworkRowMapper, category);
    }

    // Additional delete functionality
    public void deleteArtByCategory(String category) {
        String query = "DELETE FROM artwork WHERE category = ?";
        jdbcTemplate.update(query, category);
    }

    public void deleteArtByOwner(int ownerId) {
        String query = "DELETE FROM artwork WHERE owner_id = ?";
        jdbcTemplate.update(query, ownerId);
    }

    public void deleteArtByTitle(String title) {
        String query = "DELETE FROM artwork WHERE title = ?";
        jdbcTemplate.update(query, title);
    }
}
