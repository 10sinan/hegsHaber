# hegsHaber

Spring Boot tabanlı, kullanıcıların oyun oynadıkça XP ve ödüller kazandığı, sosyal bileşenler içeren bir backend servisidir. Proje; kullanıcı yönetimi, oyunlar, görevler, rozetler, sanal bahçe (pet/plant) ve haber kaydetme özellikleri barındırır.

## İçerik
- [Mimari ve Modüller](#mimari-ve-modüller)
- [Veri Modeli (Özet)](#veri-modeli-özet)
- [Gereksinimler](#gereksinimler)
- [Geliştirme ve Çalıştırma](#geliştirme-ve-çalıştırma)
- [Önemli Uç Noktalar (API)](#önemli-uç-noktalar-api)
- [XP ve Leaderboard Akışı](#xp-ve-leaderboard-akışı)
- [Sık Kullanılan Test Komutları](#sık-kullanılan-test-komutları)
- [Tüm Endpointler (İstek/Cevap Örnekleri)](#tüm-endpointler-istekcevap-örnekleri)

## Mimari ve Modüller
Projede tipik bir katmanlı mimari izlenir:
- `controller/`: REST API uç noktaları
- `service/`: İş kuralları ve transaction yönetimi
- `repository/`: Spring Data JPA ile veri erişimi
- `entity/`: JPA varlıkları (user, social, game vb.)
- `dto/`: Veri transfer objeleri (ör. Leaderboard)
- `config/`: Güvenlik ve framework yapılandırmaları

### Başlıca Domainler
- Sosyal: Oyunlar (`Game`), görevler (`Task`), rozetler (`Badge`), döşemeler (`Tiles`), sanal bahçe (`Pet`, `Plant`), kaydedilen haberler (`SavedNews`)
- Kullanıcı: Kullanıcı (`User`), kullanıcı-oyun (`UserGame`), kullanıcı XP (`UserXp`), kullanıcı coinleri (`UserCoins`), kullanıcı petleri (`UserPets`), kullanıcı rozetleri (`UserBadge`)

## Veri Modeli (Özet)
- `games`: Oyun meta verileri. Alanlar: `id`, `name`, `description`, `rewardPoints`, `rewardXp`, `isActive`, `image_url`.
- `user_games`: Kullanıcı ile oyun ilişkisi ve birikimli oyun XP’si. Alanlar: `id`, `user_uuid`, `game_id`, `status`, `xp_earned`, `created_at`.
- `user_xp`: Kullanıcının tüm oyunlardan kazandığı toplam XP. Alanlar: `id`, `user_uuid` (unique), `xp`, `created_at`, `updated_at`, `deleted_at`.

## Gereksinimler
- Java 17+
- Maven 3.8+
- Bir SQL veritabanı (application.properties ile yapılandırılır)

Veritabanı yapılandırması `src/main/resources/application.properties` içinde yapılır.

## Önemli Uç Noktalar (API)
Aşağıdaki uç noktalar `user_games` ve `user_xp` akışları için kritiktir.

### Oyun atama
- `POST /user-games/assign?userId={UUID}&gameId={Long}`
  - Kullanıcıya oyun atar, `user_games` tablosunda kayıt açar.

### Oyunu oynat (rewardXp kadar ekle)
- `POST /user-games/play?userGameId={Long}`
  - İlgili `Game.rewardXp` kadar XP’yi hem `user_games.xp_earned` hem `user_xp.xp` üzerine ekler. Transactional.
  - Cevap: `{ "addedXp": <rewardXp> }`

### Spesifik userId + gameId ile XP ekle
- `POST /user-games/add-xp-by-user-game?userId={UUID}&gameId={Long}&xp={int}`
  - İlgili `user_games` kaydını (userId+gameId) bulur, `xp_earned` artırır ve `user_xp` toplamını günceller.

### Manuel userGameId ile XP ekle
- `POST /user-games/add-xp?userGameId={Long}&xp={int}`
  - Belirli bir `user_games.id` satırına XP ekler ve `user_xp` toplamını günceller.

### Toplam XP (kullanıcı)
- `GET /user-games/total-xp/{userUuid}`
  - `user_xp` tablosundan toplam XP döner.

### Leaderboard
- `GET /user-games/leaderboard` (Toplam)
  - `user_xp` tablosundaki `xp` alanına göre ilk 10 kullanıcıyı döner.
- `GET /user-games/leaderboard?gameId={Long}` (Oyun bazlı)
  - `user_games.xp_earned` değerlerinin toplamına göre (belirli oyun için) ilk 10 kullanıcıyı döner.

`Leaderboard` DTO:
- istenılen verılerı ıstenılen sekılde doner
```

## XP ve Leaderboard Akışı
1. Kullanıcıya oyun atanır (`/user-games/assign`).
2. Kullanıcı oyunu oynadığında `/user-games/play` çağrılır: `Game.rewardXp` kadar XP hem `user_games.xp_earned` hem `user_xp.xp`’ye eklenir (tek transaction).
3. Alternatif olarak belirli miktar XP `/user-games/add-xp` veya `/user-games/add-xp-by-user-game` ile eklenebilir.
4. Toplam leaderboard `user_xp` tablosundan, oyun bazlı leaderboard ise `user_games`’ten hesaplanır.


## Tüm Endpointler (İstek/Cevap Örnekleri)

Aşağıda tüm controller'lar altındaki uç noktalar özetlenmiştir. Örnekler PowerShell `curl` ile verilmiştir.

### Auth (/auth)
- POST /auth/register
  - Body:
    ```json
    { "username": "sinan", "email": "s@example.com", "password": "123456" }
    ```
  - 201:
    ```json
    { "token": "jwt...", "user": { /* user fields */ } }
    ```
- POST /auth/login
  - Body:
    ```json
    { "email": "s@example.com", "password": "123456" }
    ```
  - 200 (Set-Cookie: jwt=...):
    ```json
    { "token": "jwt...", "user": { /* user fields */ } }
    ```
- POST /auth/logout
  - 200: "Çıkış işlemi tamamlandı"

### News (/news)
- GET /news/guncel (ve dunya, ekonomi, spor, saglik, teknoloji)
  - 200: `NewsDto[]`

### Saved News (/saved-news)
- POST /saved-news/save
  - Body: `SavedNews`
  - 201: `SavedNews`
- GET /saved-news/all
  - 200: `SavedNews[]`

### Games (/games)
- GET /games/all
  - 200: `Game[]`
- POST /games/create
  - Body: `Game`
  - 201: `Game`

### Badges (/badges)
- POST /badges/create-badge
  - Body: `Badge`
  - 201: `BadgeDto`
- GET /badges/user/{userId}
  - 200: `BadgeDto[]`
- GET /badges/all
  - 200: `BadgeDto[]`
- POST /badges/assign?badgeId={id}&userId={uuid}
  - 200: `BadgeDto` | 404

### Friendships (/friendships)
- POST /friendships/follow?followerId={uuid}&followingId={uuid}
  - 201: `FriendshipDto`
- GET /friendships/list/{userId}
  - 200: `FriendshipDto[]`
- DELETE /friendships/unfollow?followerId={uuid}&followingId={uuid}
  - 200/204

### Pets (/pets)
- POST /pets/add
  - Body: `Pet_types`
  - 201: `PetDto`
- GET /pets/all-pets
  - 200: `Pet_types[]`

### Plants (/plants)
- GET /plants/{id}
  - 200: `Plant_types` | 404
- POST /plants/addPlant
  - 201: `Plant_types`

### Tasks (/tasks)
- GET /tasks/daily-tasks
  - 200: `Task[]`
- GET /tasks/{id}
  - 200: `Task` | 404
- POST /tasks/create
  - Body: `Task`
  - 201: `Task`

### Tiles (/tiles)
- GET /tiles/{id}
  - 200: `Tiles`
- GET /tiles/all
  - 200: `Tiles[]`
- POST /tiles/create
  - 201: `Tiles`

### Users (/users)
- GET /users/{id}
  - 200: `UserDto` | 404
- GET /users/getAll
  - 200: `UserDto[]`
- GET /users/search?name={text}
  - 200: `UserDto[]`
- GET /users/count
  - 200: `number`

### User Coins (/user-coins)
- POST /user-coins/save
  - Body: `UserCoins`
  - 201: `UserCoins`
- GET /user-coins/user/{userId}
  - 200: `UserCoins[]`
- GET /user-coins/all
  - 200: `UserCoins[]`

### User Pets (/user-pets)
- GET /user-pets/by-user/{userUuid}
  - 200: `PetDto[]`
- POST /user-pets/assign?userId={uuid}&petId={id}
  - 201: "created"

### User Games (/user-games)
- POST /user-games/assign?userId={uuid}&gameId={id}
  - 201: "created"
- GET /user-games/leaderboard
  - 200: toplam leaderboard (user_xp)
  - Örnek:
    ```json
    [{ "userUuid":"...","username":"...","totalXp":1500,"gameXp":0 }]
    ```
- GET /user-games/leaderboard?gameId={id}
  - 200: oyun bazlı leaderboard (user_games)
  - Örnek:
    ```json
    [{ "userUuid":"...","username":"...","totalXp":0,"gameXp":500 }]
    ```
- GET /user-games/{userUuid}
  - 200: `UserGame[]` | 404 `[]`
- GET /user-games/total-xp/{userUuid}
  - 200: `number`
- POST /user-games/add-xp-by-user-game?userId={uuid}&gameId={id}&xp={n}
  - 200: "XP eklendi"