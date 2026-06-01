# Build stage
FROM node:22-alpine AS builder
WORKDIR /app

# Install dependencies (use package-lock if present)
COPY package*.json ./
# Allow installing packages even if package engines don't strictly match the base image
ENV NPM_CONFIG_ENGINE_STRICT=false
# Use npm install to regenerate/update lockfile when necessary inside the image.
# If you want reproducible builds, update your package-lock.json locally and keep using `npm ci`.
RUN npm install --no-audit --no-fund

# Copy source and build
COPY . .
RUN npm run build

# Runtime stage
FROM node:22-alpine AS runner
WORKDIR /app

# Runtime environment variables (can be overridden at docker run / compose)
ENV NODE_ENV=production
ENV NITRO_HOST=0.0.0.0
ENV NITRO_PORT=3000

# Copy built output and node_modules from builder
COPY --from=builder /app/.output ./.output
COPY --from=builder /app/node_modules ./node_modules

EXPOSE 3000

# Create a small entrypoint that runs the proper command depending on NODE_ENV
RUN mkdir -p /usr/local/bin && \
    printf '#!/bin/sh\nif [ "${NODE_ENV}" = "development" ] || [ "${NODE_ENV}" = "dev" ]; then\n  echo "Starting Nuxt in development mode (expect mounted source or prepared node_modules)..."\n  npm run dev -- --hostname 0.0.0.0\nelse\n  echo "Starting Nuxt Nitro production server..."\n  node .output/server/index.mjs\nfi\n' > /usr/local/bin/entrypoint.sh && \
    chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

