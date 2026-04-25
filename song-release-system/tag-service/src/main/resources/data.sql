INSERT INTO tags (id, name) VALUES ('e041bd5a-df30-4e2a-bdd7-7539ee6bc8f4', 'rock') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('6b3cfbbd-3e6f-40e1-bb5c-4386fc757523', 'pop') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('a546d1bf-65fa-4c6e-8120-038cbbbc433a', 'jazz') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('2ad8bfdf-0a56-42ab-8f96-dfa79e6ac0cb', 'classical') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('fbcc6625-fadd-4dd8-bc79-0ac98af074bf', 'electronic') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('c1b48b11-fa08-4e1e-9279-b1d4ef7bb8d3', 'hip-hop') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('884d852a-87eb-41f2-8e14-72215c0e0b3c', 'rnb') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('318f78a7-fc37-4b71-9252-c2834b6e511c', 'country') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('9ba100c5-5b43-42e8-8b92-b4c48bed9efb', 'blues') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('0a804db3-2fa6-4b68-8de1-61b6c0ac0f4b', 'metal') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('f74bc7e4-2391-4952-b91c-8b8393e1858a', 'reggae') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('56c80c2f-8d26-4ed8-b4b6-7be69d12a613', 'indie') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('2ef563b7-781c-4b53-bba8-0f0acb119ce0', 'folk') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('d8c6b889-1d44-4841-a1e6-2391b1f6308a', 'punk') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('bb0af9fa-5eb3-40a1-bf96-5dece136c348', 'ambient') ON CONFLICT (id) DO NOTHING;

-- Moods
INSERT INTO tags (id, name) VALUES ('d0b1a03e-ea0c-4860-951c-323be800e23e', 'happy') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('5a8d29b5-4b06-4f40-8f9f-cf128cebf982', 'sad') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('1ad89bf1-d52f-4882-96ea-6e06dd82283f', 'energetic') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('916a4b1e-6202-4fc8-9f17-862d85b140cd', 'chill') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('2c0e8f3b-fa2c-473d-8b01-cb863c0a5de9', 'dark') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('884c5dc5-f8e4-44ed-9ac1-60a6a21359c2', 'romantic') ON CONFLICT (id) DO NOTHING;

-- Styles / Characteristics
INSERT INTO tags (id, name) VALUES ('4859a721-a5de-4cf5-9988-af28c7dcf603', 'acoustic') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('62fbc0ee-e2cc-4a7b-a2c6-302dfdfd7003', 'live') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('b6e9e13d-5de2-4c91-b3b3-8cc26e7b6833', 'instrumental') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('eec91ba8-5ec9-4c54-bda5-74895be5d36e', 'fast') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('ab0de456-cc22-42c2-be12-6f2cf7cf7b0c', 'slow') ON CONFLICT (id) DO NOTHING;

-- Eras
INSERT INTO tags (id, name) VALUES ('272166e4-453d-4c3d-8e6c-c9f53e34b0fc', '80s') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('a30abf62-3c1c-43f6-9aa2-cc23bb1d7fbe', '90s') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('1b017b2b-5775-4d0c-a05d-ccfbd0c9d7d8', '2000s') ON CONFLICT (id) DO NOTHING;

-- Use cases / Activities
INSERT INTO tags (id, name) VALUES ('83a73c52-7901-4475-b6fc-fc071ab8256e', 'workout') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('e0ebd659-14a0-4fc7-ad27-05c0fb1eb270', 'study') ON CONFLICT (id) DO NOTHING;
INSERT INTO tags (id, name) VALUES ('f3b6c20d-327c-4814-9984-0a3be3ceceb9', 'party') ON CONFLICT (id) DO NOTHING;
