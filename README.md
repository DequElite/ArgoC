# ArgoC — Secured Socket Chat

**ArgoC** is not just another chat.
It’s a lightweight CLI tool for secure communication over sockets — прямо из терминала.

Talk directly with your friends without middle servers, using encrypted messages.

---

## ⚡ Features

* 🔐 AES encryption for all messages
* 🧠 Simple and clean CLI interface
* 🌐 Direct connection (peer-to-peer style)
* 🗂 Chat history stored in JSON
* 🧱 Clean Architecture (easy to scale and maintain)

---

## 🚀 Usage

### Build ArgoC (This will be removed in the future)
```bash
mvn package
```

### Start server

```bash
java -jar ArgoC.jar -s --port 9999 --password mySecret
```

### Start client

```bash
java -jar ArgoC.jar -c --host 127.0.0.1 --port 9999 --password mySecret
```

### History

#### Show all:
```bash
java -jar ArgoC.jar -h --show ALL
```

#### Show by id:
```bash
java -jar ArgoC.jar -h --show ONE --id ...
```

---

## ⚠️ Important

Both sides must use the **same password**, otherwise messages cannot be decrypted.

---

## 💬 Who is this for?

* developers
* CLI lovers
* anyone curious about networking & encryption

---

## 🧪 Status

Work in progress. Core architecture is ready — more features coming soon.
