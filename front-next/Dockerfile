# frontend/Dockerfile
FROM node:18-alpine

WORKDIR /app

# package.json & lock 파일 복사
COPY package*.json ./

# 의존성 설치
RUN npm install

# 소스 복사
COPY . .

# Next.js 빌드
RUN npm run build

# Next.js 포트 (기본 3000)
EXPOSE 3000

# 애플리케이션 실행
CMD ["npm", "run", "start"]