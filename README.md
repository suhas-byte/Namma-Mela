# 🎭 Namma Mela — ನಮ್ಮ ಮೇಳ
### Drama Company Digital Box-Office

> **Project Title: 13** — Android App Development using GenAI

A dedicated digital booking platform for rural village drama troupes (Company Nataka), bringing professional theater management to Karnataka's vibrant folk theater tradition.

---

## 📱 Screenshots

| Splash | Home — Tonight's Play | Seat Map | Manager Portal |
|--------|----------------------|----------|----------------|
| Gold theatrical splash | Poster + cast + fan wall | Color-coded 6×10 grid | Add plays & manage cast |

---

## 🧩 Features

### Fan-Facing
- **Tonight's Play** — Poster, title, duration, venue, show time
- **Cast List** — Horizontal scroll with photos, names and roles (Actor, Comedian, Singer)
- **Seat Map** — Interactive 6×10 grid; AVAILABLE 🟢 / RESERVED 🔴 / FRONT ROW 🟠
- **Reserve a Seat** — Enter your name, confirm — instant Room DB update
- **Fan Wall (Applause)** — Post appreciation messages for actors after the show

### Manager Portal
- Add new plays with full details (title, description, duration, venue, date/time)
- Add cast members (Lead Actor, Comedian, Singer)
- Set any scheduled play as **"Tonight's Play"** with one tap
- View all scheduled plays with active status

---

## 🏗️ Architecture

```
MVVM (Model–View–ViewModel)
├── View       → Activities + XML Layouts
├── ViewModel  → MainViewModel (LiveData + Coroutines)
└── Model      → Room Database (DAOs + Repository)
```

### Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | XML Layouts + Material Design 3 |
| Architecture | MVVM + LiveData |
| Database | Room (SQLite) |
| Image Loading | Glide 4 |
| Async | Kotlin Coroutines |
| Navigation | AndroidX Navigation |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 34 (Android 14) |

---

## 📁 Project Structure

```
NammaMela/
├── app/src/main/
│   ├── java/com/nammamela/
│   │   ├── data/
│   │   │   ├── models/          # Play, CastMember, Seat, Applause (Room @Entity)
│   │   │   └── repository/      # DAOs, NammaMelaDatabase, Repository
│   │   └── ui/
│   │       ├── activities/      # Splash, Main, SeatMap, Manager, ViewModel
│   │       └── adapters/        # Cast, Seat, Applause, PlayManager RecyclerView adapters
│   └── res/
│       ├── layout/              # 8 XML layouts
│       ├── drawable/            # Seat drawables, icons, poster placeholder
│       ├── values/              # colors (gold/dark theme), strings, themes
│       └── font/                # Cinzel (downloadable Google Font)
```

---

## 🚀 Setup & Run

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK 34

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/NammaMela.git
cd NammaMela

# 2. Open in Android Studio
#    File → Open → select the NammaMela folder

# 3. Let Gradle sync (it will download all dependencies automatically)

# 4. Run on emulator or device (API 24+)
#    Run → Run 'app'
```

### First Launch
The app auto-populates a sample play **"Shakuntala"** with 4 cast members and 60 seats (6 rows × 10 seats) on first install, so you can explore all features immediately.

---

## 🎨 Design System

| Token | Value | Usage |
|-------|-------|-------|
| `gold` | `#F5C842` | Primary brand, buttons, headings |
| `bg_dark` | `#12100E` | Screen background |
| `bg_card` | `#1E1B18` | Cards, toolbar |
| `text_primary` | `#E8E0D0` | Body text |
| `seat_available` | `#2E7D32` | Available seats |
| `seat_reserved` | `#B71C1C` | Booked seats |
| `seat_frontrow` | `#E65100` | Premium front row |
| Font | Cinzel (Google Fonts) | Headings — theatrical serif |

---

## 🌱 Impact Goals

| Goal | Description |
|------|-------------|
| **Cultural Economy** | Strengthens the traditional theater industry |
| **Digital Management** | Introduces formal systems to unorganized art sector |
| **Community Entertainment** | Ensures rural communities have access to organized arts |

---

## ✅ Success Criteria (from project spec)

- [x] Seat Map updates in real-time when a seat is Reserved (Room LiveData)
- [x] Tonight's Play is easily updatable by the manager (one-tap in Manager Portal)
- [x] UI is theatrical and bold (dark gold theme, Cinzel font, dramatic layout)

---

## 📖 Database Schema

```
plays          → id, title, description, duration, venue, showDate, showTime, posterUrl, isActive
cast_members   → id, playId (FK), name, role, photoUrl, bio
seats          → id, playId (FK), rowLabel, seatNumber, status, bookedByName, bookedAt
applause       → id, playId (FK), fanName, message, emoji, postedAt
```

---

## 👨‍💻 Developer Notes

- **Fonts**: The app uses Cinzel via Android Downloadable Fonts (Google Fonts). Requires internet on first run to cache the font; falls back gracefully.
- **Images**: Posters load via Glide. You can pass a URL or a local drawable resource name.
- **Seat initialization**: Seats are auto-generated per play on first insert (6 rows A–F, 10 seats each). Row A is pre-marked as FRONT_ROW.

---

## 📄 License

MIT License — free to use for educational and community projects.

---

*Built with ❤️ for Karnataka's village drama tradition — ಜಾನಪದ ನಾಟಕ ಕಲೆಗೆ ಡಿಜಿಟಲ್ ಶಕ್ತಿ*
