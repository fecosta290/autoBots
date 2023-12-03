# Atv-Desenvolvimento-Web-3

- Após iniciar, abrir insomnia e colocar a rota Post http://localhost:8080/login
- Usar o seguinte Json:
{
	"nomeUsuario": "admin",
	"senha": "123456"
}

- Haverá uma resposta como essa:
{
	"Authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NzAzNTE1M30.dZy3DD6xKtujn5vuqivJ4219VZhLBOBRwHAeBDztCiTy7vIYVnoJZklarbIRjfkHH-DCnPBPvJMA4TNj4QmgGw"
}

- copie do Bearer até o fim do token e coloque no Authorization do header do insomnia
