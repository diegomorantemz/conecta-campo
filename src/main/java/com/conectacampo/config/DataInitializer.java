package com.conectacampo.config;

import com.conectacampo.model.*;
import com.conectacampo.model.enums.HarvestStatus;
import com.conectacampo.model.enums.Role;
import com.conectacampo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final ProductRepository productRepository;
    private final HarvestRepository harvestRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("🚀 ========== INICIANDO DATA INITIALIZER ==========");

        // ==========================================
        // 1. Crear usuario ADMINISTRADOR
        // ==========================================
        if (userRepository.count() == 0) {
            log.info("📝 Creando usuario administrador...");

            User admin = new User();
            admin.setName("Administrador");
            admin.setLastname("Sistema");
            admin.setEmail("admin@conectacampo.pe");
            admin.setPassword("admin123");
            admin.setRole(Role.ADMIN);
            admin.setPhone("987654321");
            admin.setDistrict("Piura");
            admin.setEnabled(true);
            userRepository.save(admin);

            log.info("✅ Administrador creado: admin@conectacampo.pe / admin123");
        }

        // ==========================================
        // 2. Crear PRODUCTOS de Piura
        // ==========================================
        if (productRepository.count() == 0) {
            log.info("📦 Cargando catálogo de productos...");

            createProduct("Mango", "Criollo", "Fruta", "kg", new BigDecimal("1.20"), new BigDecimal("1.80"), "03", "05");
            createProduct("Mango", "Kent", "Fruta", "kg", new BigDecimal("1.80"), new BigDecimal("2.50"), "03", "05");
            createProduct("Plátano", "De Seda", "Fruta", "kg", new BigDecimal("2.00"), new BigDecimal("3.00"), "01", "12");
            createProduct("Limón", "Sutil", "Cítrico", "kg", new BigDecimal("1.50"), new BigDecimal("2.50"), "01", "12");
            createProduct("Arroz", "Cáscara", "Grano", "kg", new BigDecimal("1.50"), new BigDecimal("2.20"), "01", "04");
            createProduct("Maíz", "Amarillo", "Grano", "kg", new BigDecimal("1.80"), new BigDecimal("2.50"), "01", "04");
            createProduct("Café", "De Altura", "Especial", "kg", new BigDecimal("5.00"), new BigDecimal("8.00"), "03", "08");
            createProduct("Cacao", "Fino de Aroma", "Especial", "kg", new BigDecimal("8.00"), new BigDecimal("12.00"), "05", "10");
            createProduct("Cebolla", "Roja", "Hortaliza", "kg", new BigDecimal("1.50"), new BigDecimal("2.50"), "05", "09");

            log.info("✅ {} productos cargados", productRepository.count());
        }

        // ==========================================
        // 3. Crear AGRICULTORES de ejemplo
        // ==========================================
        if (userRepository.findByRole(Role.FARMER).isEmpty()) {
            log.info("👨‍🌾 Creando agricultores de ejemplo...");

            // Agricultor 1: Juan Pérez (Chulucanas)
            User farmer1 = new User();
            farmer1.setName("Juan");
            farmer1.setLastname("Pérez");
            farmer1.setEmail("juan@conectacampo.pe");
            farmer1.setPassword("123456");
            farmer1.setRole(Role.FARMER);
            farmer1.setPhone("987654321");
            farmer1.setDistrict("Chulucanas");
            farmer1.setEnabled(true);
            userRepository.save(farmer1);
            log.info("   ✅ Agricultor: Juan Pérez (Chulucanas)");

            // Agricultor 2: María Rodríguez (Tambogrande)
            User farmer2 = new User();
            farmer2.setName("María");
            farmer2.setLastname("Rodríguez");
            farmer2.setEmail("maria@conectacampo.pe");
            farmer2.setPassword("123456");
            farmer2.setRole(Role.FARMER);
            farmer2.setPhone("987123456");
            farmer2.setDistrict("Tambogrande");
            farmer2.setEnabled(true);
            userRepository.save(farmer2);
            log.info("   ✅ Agricultor: María Rodríguez (Tambogrande)");

            // Agricultor 3: Comunidad Huancabamba (Café)
            User farmer3 = new User();
            farmer3.setName("Comunidad");
            farmer3.setLastname("Huancabamba");
            farmer3.setEmail("huancabamba@conectacampo.pe");
            farmer3.setPassword("123456");
            farmer3.setRole(Role.FARMER);
            farmer3.setPhone("973123456");
            farmer3.setDistrict("Huancabamba");
            farmer3.setEnabled(true);
            userRepository.save(farmer3);
            log.info("   ✅ Agricultor: Comunidad Huancabamba");

            log.info("✅ Total agricultores creados: 3");
        }

        // ==========================================
        // 4. Crear FINCAS para los agricultores
        // ==========================================
        if (farmRepository.count() == 0) {
            log.info("🏠 Creando fincas...");

            User juan = userRepository.findByEmail("juan@conectacampo.pe").orElse(null);
            User maria = userRepository.findByEmail("maria@conectacampo.pe").orElse(null);
            User comunidad = userRepository.findByEmail("huancabamba@conectacampo.pe").orElse(null);

            // Finca de Juan Pérez en Chulucanas
            if (juan != null) {
                Farm farm1 = new Farm();
                farm1.setUser(juan);
                farm1.setName("Finca El Mango");
                farm1.setDistrict("Chulucanas");
                farm1.setLocationLat(-5.0925);
                farm1.setLocationLng(-80.1625);
                farm1.setDescription("Productores de mango criollo, mango Kent y plátano de seda");
                farmRepository.save(farm1);
                log.info("   ✅ Finca creada: Finca El Mango (Chulucanas)");
            }

            // Finca de María Rodríguez en Tambogrande
            if (maria != null) {
                Farm farm2 = new Farm();
                farm2.setUser(maria);
                farm2.setName("Finca Los Limones");
                farm2.setDistrict("Tambogrande");
                farm2.setLocationLat(-4.9244);
                farm2.setLocationLng(-80.3414);
                farm2.setDescription("Productores de limón sutil y maíz amarillo");
                farmRepository.save(farm2);
                log.info("   ✅ Finca creada: Finca Los Limones (Tambogrande)");
            }

            // Finca de la Comunidad Huancabamba
            if (comunidad != null) {
                Farm farm3 = new Farm();
                farm3.setUser(comunidad);
                farm3.setName("Cafetal Huancabamba");
                farm3.setDistrict("Huancabamba");
                farm3.setLocationLat(-5.2400);
                farm3.setLocationLng(-79.4500);
                farm3.setDescription("Café de altura y cacao fino de aroma, comercio justo");
                farmRepository.save(farm3);
                log.info("   ✅ Finca creada: Cafetal Huancabamba (Huancabamba)");
            }

            log.info("✅ {} fincas creadas", farmRepository.count());
        }

        // ==========================================
        // 5. Crear COSECHAS de ejemplo
        // ==========================================
        if (harvestRepository.count() == 0) {
            log.info("🌾 Creando cosechas de ejemplo...");

            Farm fincaMango = farmRepository.findById(1L).orElse(null);
            Farm fincaLimones = farmRepository.findById(2L).orElse(null);
            Farm fincaCafe = farmRepository.findById(3L).orElse(null);

            Product mangoCriollo = productRepository.findByNameAndVariety("Mango", "Criollo").orElse(null);
            Product mangoKent = productRepository.findByNameAndVariety("Mango", "Kent").orElse(null);
            Product platano = productRepository.findByNameAndVariety("Plátano", "De Seda").orElse(null);
            Product limon = productRepository.findByNameAndVariety("Limón", "Sutil").orElse(null);
            Product cafe = productRepository.findByNameAndVariety("Café", "De Altura").orElse(null);
            Product cacao = productRepository.findByNameAndVariety("Cacao", "Fino de Aroma").orElse(null);

            // Cosecha 1: Mango Criollo - Finca El Mango
            if (fincaMango != null && mangoCriollo != null) {
                Harvest h1 = new Harvest();
                h1.setFarm(fincaMango);
                h1.setProduct(mangoCriollo);
                h1.setQuantity(new BigDecimal("500"));
                h1.setPriceSuggested(new BigDecimal("1.50"));
                h1.setHarvestDate(LocalDate.of(2026, 4, 15));
                h1.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h1);
                log.info("   ✅ Cosecha: 500kg Mango Criollo - Chulucanas");
            }

            // Cosecha 2: Mango Kent - Finca El Mango
            if (fincaMango != null && mangoKent != null) {
                Harvest h2 = new Harvest();
                h2.setFarm(fincaMango);
                h2.setProduct(mangoKent);
                h2.setQuantity(new BigDecimal("300"));
                h2.setPriceSuggested(new BigDecimal("2.20"));
                h2.setHarvestDate(LocalDate.of(2026, 4, 20));
                h2.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h2);
                log.info("   ✅ Cosecha: 300kg Mango Kent - Chulucanas");
            }

            // Cosecha 3: Plátano - Finca El Mango
            if (fincaMango != null && platano != null) {
                Harvest h3 = new Harvest();
                h3.setFarm(fincaMango);
                h3.setProduct(platano);
                h3.setQuantity(new BigDecimal("200"));
                h3.setPriceSuggested(new BigDecimal("2.50"));
                h3.setHarvestDate(LocalDate.of(2026, 4, 10));
                h3.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h3);
                log.info("   ✅ Cosecha: 200kg Plátano de Seda - Chulucanas");
            }

            // Cosecha 4: Limón - Finca Los Limones
            if (fincaLimones != null && limon != null) {
                Harvest h4 = new Harvest();
                h4.setFarm(fincaLimones);
                h4.setProduct(limon);
                h4.setQuantity(new BigDecimal("300"));
                h4.setPriceSuggested(new BigDecimal("2.00"));
                h4.setHarvestDate(LocalDate.of(2026, 3, 25));
                h4.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h4);
                log.info("   ✅ Cosecha: 300kg Limón Sutil - Tambogrande");
            }

            // Cosecha 5: Café - Cafetal Huancabamba
            if (fincaCafe != null && cafe != null) {
                Harvest h5 = new Harvest();
                h5.setFarm(fincaCafe);
                h5.setProduct(cafe);
                h5.setQuantity(new BigDecimal("150"));
                h5.setPriceSuggested(new BigDecimal("6.50"));
                h5.setHarvestDate(LocalDate.of(2026, 4, 5));
                h5.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h5);
                log.info("   ✅ Cosecha: 150kg Café de Altura - Huancabamba ✨");
            }

            // Cosecha 6: Cacao - Cafetal Huancabamba
            if (fincaCafe != null && cacao != null) {
                Harvest h6 = new Harvest();
                h6.setFarm(fincaCafe);
                h6.setProduct(cacao);
                h6.setQuantity(new BigDecimal("80"));
                h6.setPriceSuggested(new BigDecimal("10.00"));
                h6.setHarvestDate(LocalDate.of(2026, 5, 1));
                h6.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h6);
                log.info("   ✅ Cosecha: 80kg Cacao Fino de Aroma - Huancabamba ✨");
            }

            log.info("✅ {} cosechas creadas", harvestRepository.count());
        }

        log.info("🎉 ========== DATA INITIALIZER FINALIZADO ==========");
        log.info("📊 Resumen: {} usuarios | {} productos | {} fincas | {} cosechas",
                userRepository.count(), productRepository.count(),
                farmRepository.count(), harvestRepository.count());
    }

    private void createProduct(String name, String variety, String category, String unit,
                               BigDecimal minPrice, BigDecimal maxPrice, String seasonStart, String seasonEnd) {
        Product product = new Product();
        product.setName(name);
        product.setVariety(variety);
        product.setCategory(category);
        product.setUnit(unit);
        product.setMinPrice(minPrice);
        product.setMaxPrice(maxPrice);
        product.setSeasonStart(seasonStart);
        product.setSeasonEnd(seasonEnd);
        productRepository.save(product);
    }
}