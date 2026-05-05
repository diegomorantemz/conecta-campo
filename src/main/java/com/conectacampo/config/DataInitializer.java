package com.conectacampo.config;

import com.conectacampo.model.*;
import com.conectacampo.model.enums.FairStatus;
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
    private final DepartmentRepository departmentRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final FairRepository fairRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("INICIANDO DATA INITIALIZER");

        // Crear usuario ADMINISTRADOR
        if (userRepository.count() == 0) {
            log.info("Creando usuario administrador...");

            User admin = new User();
            admin.setName("Administrador");
            admin.setLastname("Sistema");
            admin.setEmail("admin@conectacampo.pe");
            admin.setPassword("admin123");
            admin.setRole(Role.ADMIN);
            admin.setPhone("987654321");
            admin.setDni("00000000");
            admin.setDistrict("Piura");
            admin.setEnabled(true);
            userRepository.save(admin);

            log.info("Administrador creado: admin@conectacampo.pe / admin123");
        }

        // Crear PRODUCTOS
        if (productRepository.count() == 0) {
            log.info("Cargando catálogo de productos...");

            createProduct("Mango", "Criollo", "Fruta", "kg", new BigDecimal("1.20"), new BigDecimal("1.80"), "03", "05");
            createProduct("Mango", "Kent", "Fruta", "kg", new BigDecimal("1.80"), new BigDecimal("2.50"), "03", "05");
            createProduct("Plátano", "De Seda", "Fruta", "kg", new BigDecimal("2.00"), new BigDecimal("3.00"), "01", "12");
            createProduct("Limón", "Sutil", "Cítrico", "kg", new BigDecimal("1.50"), new BigDecimal("2.50"), "01", "12");
            createProduct("Arroz", "Cáscara", "Grano", "kg", new BigDecimal("1.50"), new BigDecimal("2.20"), "01", "04");
            createProduct("Maíz", "Amarillo", "Grano", "kg", new BigDecimal("1.80"), new BigDecimal("2.50"), "01", "04");
            createProduct("Café", "De Altura", "Especial", "kg", new BigDecimal("5.00"), new BigDecimal("8.00"), "03", "08");
            createProduct("Cacao", "Fino de Aroma", "Especial", "kg", new BigDecimal("8.00"), new BigDecimal("12.00"), "05", "10");
            createProduct("Cebolla", "Roja", "Hortaliza", "kg", new BigDecimal("1.50"), new BigDecimal("2.50"), "05", "09");

            log.info("{} productos cargados", productRepository.count());
        }

        // Crear AGRICULTORES de ejemplo
        if (userRepository.findByRole(Role.FARMER).isEmpty()) {
            log.info("Creando agricultores de ejemplo...");

            User farmer1 = new User();
            farmer1.setName("Juan");
            farmer1.setLastname("Pérez");
            farmer1.setEmail("juan@conectacampo.pe");
            farmer1.setPassword("123456");
            farmer1.setRole(Role.FARMER);
            farmer1.setPhone("987654321");
            farmer1.setDni("12345678");
            farmer1.setDistrict("Chulucanas");
            farmer1.setEnabled(true);
            userRepository.save(farmer1);
            log.info("Agricultor: Juan Pérez (Chulucanas)");

            User farmer2 = new User();
            farmer2.setName("María");
            farmer2.setLastname("Rodríguez");
            farmer2.setEmail("maria@conectacampo.pe");
            farmer2.setPassword("123456");
            farmer2.setRole(Role.FARMER);
            farmer2.setPhone("987123456");
            farmer2.setDni("87654321");
            farmer2.setDistrict("Tambogrande");
            farmer2.setEnabled(true);
            userRepository.save(farmer2);
            log.info("Agricultor: María Rodríguez (Tambogrande)");

            User farmer3 = new User();
            farmer3.setName("Comunidad");
            farmer3.setLastname("Huancabamba");
            farmer3.setEmail("huancabamba@conectacampo.pe");
            farmer3.setPassword("123456");
            farmer3.setRole(Role.FARMER);
            farmer3.setPhone("973123456");
            farmer3.setDni("11111111");
            farmer3.setDistrict("Huancabamba");
            farmer3.setEnabled(true);
            userRepository.save(farmer3);
            log.info("Agricultor: Comunidad Huancabamba");

            log.info("Total agricultores creados: 3");
        }

        // Crear FINCAS para los agricultores
        if (farmRepository.count() == 0) {
            log.info("Creando fincas...");

            User juan = userRepository.findByEmail("juan@conectacampo.pe").orElse(null);
            User maria = userRepository.findByEmail("maria@conectacampo.pe").orElse(null);
            User comunidad = userRepository.findByEmail("huancabamba@conectacampo.pe").orElse(null);

            if (juan != null) {
                Farm farm1 = new Farm();
                farm1.setUser(juan);
                farm1.setName("Finca El Mango");
                farm1.setDistrict("Chulucanas");
                farm1.setLocationLat(-5.0925);
                farm1.setLocationLng(-80.1625);
                farm1.setDescription("Productores de mango criollo, mango Kent y plátano de seda");
                farmRepository.save(farm1);
                log.info("Finca creada: Finca El Mango (Chulucanas)");
            }

            if (maria != null) {
                Farm farm2 = new Farm();
                farm2.setUser(maria);
                farm2.setName("Finca Los Limones");
                farm2.setDistrict("Tambogrande");
                farm2.setLocationLat(-4.9244);
                farm2.setLocationLng(-80.3414);
                farm2.setDescription("Productores de limón sutil y maíz amarillo");
                farmRepository.save(farm2);
                log.info("Finca creada: Finca Los Limones (Tambogrande)");
            }

            if (comunidad != null) {
                Farm farm3 = new Farm();
                farm3.setUser(comunidad);
                farm3.setName("Cafetal Huancabamba");
                farm3.setDistrict("Huancabamba");
                farm3.setLocationLat(-5.2400);
                farm3.setLocationLng(-79.4500);
                farm3.setDescription("Café de altura y cacao fino de aroma, comercio justo");
                farmRepository.save(farm3);
                log.info("Finca creada: Cafetal Huancabamba (Huancabamba)");
            }

            log.info("{} fincas creadas", farmRepository.count());
        }

        // Crear COSECHAS de ejemplo
        if (harvestRepository.count() == 0) {
            log.info("Creando cosechas de ejemplo...");

            Farm fincaMango = farmRepository.findById(1L).orElse(null);
            Farm fincaLimones = farmRepository.findById(2L).orElse(null);
            Farm fincaCafe = farmRepository.findById(3L).orElse(null);

            Product mangoCriollo = productRepository.findByNameAndVariety("Mango", "Criollo").orElse(null);
            Product mangoKent = productRepository.findByNameAndVariety("Mango", "Kent").orElse(null);
            Product platano = productRepository.findByNameAndVariety("Plátano", "De Seda").orElse(null);
            Product limon = productRepository.findByNameAndVariety("Limón", "Sutil").orElse(null);
            Product cafe = productRepository.findByNameAndVariety("Café", "De Altura").orElse(null);
            Product cacao = productRepository.findByNameAndVariety("Cacao", "Fino de Aroma").orElse(null);

            if (fincaMango != null && mangoCriollo != null) {
                Harvest h1 = new Harvest();
                h1.setFarm(fincaMango);
                h1.setProduct(mangoCriollo);
                h1.setQuantity(new BigDecimal("500"));
                h1.setPriceSuggested(new BigDecimal("1.50"));
                h1.setHarvestDate(LocalDate.of(2026, 4, 15));
                h1.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h1);
                log.info("Cosecha: 500kg Mango Criollo - Chulucanas");
            }

            if (fincaMango != null && mangoKent != null) {
                Harvest h2 = new Harvest();
                h2.setFarm(fincaMango);
                h2.setProduct(mangoKent);
                h2.setQuantity(new BigDecimal("300"));
                h2.setPriceSuggested(new BigDecimal("2.20"));
                h2.setHarvestDate(LocalDate.of(2026, 4, 20));
                h2.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h2);
                log.info("Cosecha: 300kg Mango Kent - Chulucanas");
            }

            if (fincaMango != null && platano != null) {
                Harvest h3 = new Harvest();
                h3.setFarm(fincaMango);
                h3.setProduct(platano);
                h3.setQuantity(new BigDecimal("200"));
                h3.setPriceSuggested(new BigDecimal("2.50"));
                h3.setHarvestDate(LocalDate.of(2026, 4, 10));
                h3.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h3);
                log.info("Cosecha: 200kg Plátano de Seda - Chulucanas");
            }

            if (fincaLimones != null && limon != null) {
                Harvest h4 = new Harvest();
                h4.setFarm(fincaLimones);
                h4.setProduct(limon);
                h4.setQuantity(new BigDecimal("300"));
                h4.setPriceSuggested(new BigDecimal("2.00"));
                h4.setHarvestDate(LocalDate.of(2026, 3, 25));
                h4.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h4);
                log.info("Cosecha: 300kg Limón Sutil - Tambogrande");
            }

            if (fincaCafe != null && cafe != null) {
                Harvest h5 = new Harvest();
                h5.setFarm(fincaCafe);
                h5.setProduct(cafe);
                h5.setQuantity(new BigDecimal("150"));
                h5.setPriceSuggested(new BigDecimal("6.50"));
                h5.setHarvestDate(LocalDate.of(2026, 4, 5));
                h5.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h5);
                log.info("Cosecha: 150kg Café de Altura - Huancabamba ✨");
            }

            if (fincaCafe != null && cacao != null) {
                Harvest h6 = new Harvest();
                h6.setFarm(fincaCafe);
                h6.setProduct(cacao);
                h6.setQuantity(new BigDecimal("80"));
                h6.setPriceSuggested(new BigDecimal("10.00"));
                h6.setHarvestDate(LocalDate.of(2026, 5, 1));
                h6.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(h6);
                log.info("Cosecha: 80kg Cacao Fino de Aroma - Huancabamba ✨");
            }

            log.info("{} cosechas creadas", harvestRepository.count());
        }

        // Cargar ubicaciones de Piura
        if (departmentRepository.count() == 0) {
            log.info("Cargando ubicaciones de Piura...");

            // Crear Departamento de Piura
            Department piura = new Department();
            piura.setName("Piura");
            departmentRepository.save(piura);
            log.info("Departamento creado: Piura");

            // Provincia: Piura
            Province piuraProv = new Province();
            piuraProv.setName("Piura");
            piuraProv.setDepartment(piura);
            provinceRepository.save(piuraProv);
            log.info("Provincia creada: Piura");

            String[] piuraDistricts = {"Piura", "Castilla", "Catacaos", "Cura Mori", "El Tallán",
                    "La Arena", "Las Lomas", "La Unión", "Tambogrande", "Veintiséis de Octubre"};
            for (String districtName : piuraDistricts) {
                District district = new District();
                district.setName(districtName);
                district.setProvince(piuraProv);
                districtRepository.save(district);
            }
            log.info("10 distritos de Piura cargados");

            // Provincia: Sullana
            Province sullana = new Province();
            sullana.setName("Sullana");
            sullana.setDepartment(piura);
            provinceRepository.save(sullana);
            log.info("Provincia creada: Sullana");

            String[] sullanaDistricts = {"Sullana", "Bellavista", "Ignacio Escudero", "Lancones",
                    "Marcavelica", "Miguel Checa", "Querecotillo", "Salitral"};
            for (String districtName : sullanaDistricts) {
                District district = new District();
                district.setName(districtName);
                district.setProvince(sullana);
                districtRepository.save(district);
            }
            log.info("8 distritos de Sullana cargados");

            // Provincia: Morropón
            Province morropon = new Province();
            morropon.setName("Morropón");
            morropon.setDepartment(piura);
            provinceRepository.save(morropon);
            log.info("Provincia creada: Morropón");

            String[] morroponDistricts = {"Chulucanas", "Buenos Aires", "La Matanza", "Morropón",
                    "Salitral", "San Juan de Bigote", "Santa Catalina de Mossa", "Santo Domingo", "Yamango"};
            for (String districtName : morroponDistricts) {
                District district = new District();
                district.setName(districtName);
                district.setProvince(morropon);
                districtRepository.save(district);
            }
            log.info("9 distritos de Morropón cargados");

            // Provincia: Huancabamba=
            Province huancabamba = new Province();
            huancabamba.setName("Huancabamba");
            huancabamba.setDepartment(piura);
            provinceRepository.save(huancabamba);
            log.info("Provincia creada: Huancabamba");

            String[] huancabambaDistricts = {"Huancabamba", "Canchaque", "El Carmen de la Frontera",
                    "Huarmaca", "Lalaquiz", "San Miguel de El Faique", "Sondor", "Sondorillo"};
            for (String districtName : huancabambaDistricts) {
                District district = new District();
                district.setName(districtName);
                district.setProvince(huancabamba);
                districtRepository.save(district);
            }
            log.info("8 distritos de Huancabamba cargados");

            // Provincia: Ayabaca
            Province ayabaca = new Province();
            ayabaca.setName("Ayabaca");
            ayabaca.setDepartment(piura);
            provinceRepository.save(ayabaca);
            log.info("Provincia creada: Ayabaca");
            String[] ayabacaDistricts = {"Ayabaca", "Frías", "Jilili", "Lagunas", "Montero",
                    "Pacaipampa", "Paimas", "Sapillica", "Sícchez", "Suyo"};
            for (String districtName : ayabacaDistricts) {
                District district = new District();
                district.setName(districtName);
                district.setProvince(ayabaca);
                districtRepository.save(district);
            }
            log.info("10 distritos de Ayabaca cargados");

            // Provincia: Talara
            Province talara = new Province();
            talara.setName("Talara");
            talara.setDepartment(piura);
            provinceRepository.save(talara);
            log.info("Provincia creada: Talara");

            String[] talaraDistricts = {"Talara", "El Alto", "La Brea", "Lobitos", "Los Órganos", "Máncora"};
            for (String districtName : talaraDistricts) {
                District district = new District();
                district.setName(districtName);
                district.setProvince(talara);
                districtRepository.save(district);
            }
            log.info("6 distritos de Talara cargados");

            // Provincia: Paita
            Province paita = new Province();
            paita.setName("Paita");
            paita.setDepartment(piura);
            provinceRepository.save(paita);
            log.info("Provincia creada: Paita");

            String[] paitaDistricts = {"Paita", "Amotape", "Arenal", "Colán", "La Huaca", "Tamarindo", "Vichayal"};
            for (String districtName : paitaDistricts) {
                District district = new District();
                district.setName(districtName);
                district.setProvince(paita);
                districtRepository.save(district);
            }
            log.info("7 distritos de Paita cargados");

            // Provincia: Sechura
            Province sechura = new Province();
            sechura.setName("Sechura");
            sechura.setDepartment(piura);
            provinceRepository.save(sechura);
            log.info("Provincia creada: Sechura");

            String[] sechuraDistricts = {"Sechura", "Bellavista de la Unión", "Bernal",
                    "Cristo Nos Valga", "Rinconada Llicuar", "Vice"};
            for (String districtName : sechuraDistricts) {
                District district = new District();
                district.setName(districtName);
                district.setProvince(sechura);
                districtRepository.save(district);
            }
            log.info("6 distritos de Sechura cargados");

            log.info("Ubicaciones cargadas: 1 departamento, 8 provincias, 65 distritos");
        } else {
            log.info("Ubicaciones ya existen en la BD, saltando carga...");
        }

        // Crear FERIAS de ejemplo
        if (fairRepository.count() == 0) {
            log.info("Creando ferias de ejemplo...");

            // Obtener un agricultor para asociar las ferias
            User farmer = userRepository.findByEmail("juan@conectacampo.pe").orElse(null);
            if (farmer == null) {
                farmer = userRepository.findByRole(Role.FARMER).stream().findFirst().orElse(null);
            }

            if (farmer != null) {

                Fair fair1 = new Fair();
                fair1.setFarmer(farmer);
                fair1.setTitle("Feria de Chulucanas");
                fair1.setDescription("Ven y apoya a nuestros agricultores locales. Encontrarás mangos, plátanos y más productos frescos.");
                fair1.setLocationName("Plaza Principal de Chulucanas");
                fair1.setLocationLat(-5.0925);
                fair1.setLocationLng(-80.1625);
                fair1.setFairDate(LocalDate.of(2026, 5, 25));
                fair1.setStartTime(java.time.LocalTime.of(8, 0));
                fair1.setEndTime(java.time.LocalTime.of(14, 0));
                fair1.setStatus(FairStatus.UPCOMING);
                fairRepository.save(fair1);
                log.info("Feria creada: Feria de Chulucanas (25/05/2026)");

                Fair fair2 = new Fair();
                fair2.setFarmer(farmer);
                fair2.setTitle("Feria de Tambogrande");
                fair2.setDescription("Productos frescos directo del campo. Limones, mangos y maíz de temporada.");
                fair2.setLocationName("Mercado Central de Tambogrande");
                fair2.setLocationLat(-4.9244);
                fair2.setLocationLng(-80.3414);
                fair2.setFairDate(LocalDate.of(2026, 5, 26));
                fair2.setStartTime(java.time.LocalTime.of(9, 0));
                fair2.setEndTime(java.time.LocalTime.of(15, 0));
                fair2.setStatus(FairStatus.UPCOMING);
                fairRepository.save(fair2);
                log.info("Feria creada: Feria de Tambogrande (26/05/2026)");

                Fair fair3 = new Fair();
                fair3.setFarmer(farmer);
                fair3.setTitle("Feria Campesina - Morropón");
                fair3.setDescription("Cacao, café y productos de la sierra piurana. Productos de comercio justo.");
                fair3.setLocationName("Plaza de Armas de Morropón");
                fair3.setLocationLat(-5.2000);
                fair3.setLocationLng(-80.4000);
                fair3.setFairDate(LocalDate.of(2026, 6, 1));
                fair3.setStartTime(java.time.LocalTime.of(7, 0));
                fair3.setEndTime(java.time.LocalTime.of(13, 0));
                fair3.setStatus(FairStatus.UPCOMING);
                fairRepository.save(fair3);
                log.info("Feria creada: Feria Campesina - Morropón (01/06/2026)");

                log.info("{} ferias creadas", fairRepository.count());
            } else {
                log.warn("No se encontraron agricultores para crear ferias");
            }
        } else {
            log.info("Ferias ya existen en la BD, saltando carga...");
        }

        log.info("DATA INITIALIZER FINALIZADO ");
        log.info("Resumen: {} usuarios | {} productos | {} fincas | {} cosechas ",
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