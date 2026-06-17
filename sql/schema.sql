--
-- Structure de la table `artist`
--

CREATE TABLE `artist` (
                          `id` int(11) NOT NULL,
                          `name` varchar(255) NOT NULL,
                          `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                          `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `sale`
--

CREATE TABLE `sale` (
                        `id` int(11) NOT NULL,
                        `id_user` int(11) NOT NULL,
                        `sale_date` timestamp NOT NULL DEFAULT current_timestamp(),
                        `total_amount` decimal(10,2) NOT NULL,
                        `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                        `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `sale_items`
--

CREATE TABLE `sale_items` (
                              `id` int(11) NOT NULL,
                              `id_sale` int(11) NOT NULL,
                              `id_vinyl` int(11) NOT NULL,
                              `quantity` int(11) NOT NULL,
                              `unit_price` decimal(10,2) NOT NULL,
                              `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                              `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
                          `id` int(11) NOT NULL,
                          `name` varchar(255) NOT NULL,
                          `email` varchar(255) NOT NULL,
                          `email_verified_at` timestamp NULL DEFAULT NULL,
                          `password` varchar(255) NOT NULL,
                          `is_admin` tinyint(1) DEFAULT 0,
                          `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                          `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour la table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

-- --------------------------------------------------------

--
-- Structure de la table `vinyl`
--

CREATE TABLE `vinyl` (
                         `id` int(11) NOT NULL,
                         `title` varchar(255) NOT NULL,
                         `id_artist` int(11) NOT NULL,
                         `genre` varchar(100) DEFAULT NULL,
                         `release_year` int(11) DEFAULT NULL,
                         `price` decimal(10,2) NOT NULL,
                         `quantity` int(11) DEFAULT 0,
                         `is_for_sale` tinyint(1) DEFAULT 1,
                         `is_sold` tinyint(1) DEFAULT 0,
                         `is_out_of_stock` tinyint(1) DEFAULT 0,
                         `is_reserved` tinyint(1) DEFAULT 0,
                         `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                         `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `artist`
--
ALTER TABLE `artist`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `sale`
--
ALTER TABLE `sale`
    ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `sale_items`
--
ALTER TABLE `sale_items`
    ADD PRIMARY KEY (`id`),
  ADD KEY `id_sale` (`id_sale`),
  ADD KEY `id_vinyl` (`id_vinyl`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Index pour la table `vinyl`
--
ALTER TABLE `vinyl`
    ADD PRIMARY KEY (`id`),
  ADD KEY `id_artist` (`id_artist`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `artist`
--
ALTER TABLE `artist`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `sale`
--
ALTER TABLE `sale`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `sale_items`
--
ALTER TABLE `sale_items`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `vinyl`
--
ALTER TABLE `vinyl`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `sale`
--
ALTER TABLE `sale`
    ADD CONSTRAINT `sale_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `sale_items`
--
ALTER TABLE `sale_items`
    ADD CONSTRAINT `sale_items_ibfk_1` FOREIGN KEY (`id_sale`) REFERENCES `sale` (`id`),
  ADD CONSTRAINT `sale_items_ibfk_2` FOREIGN KEY (`id_vinyl`) REFERENCES `vinyl` (`id`);

--
-- Contraintes pour la table `vinyl`
--
ALTER TABLE `vinyl`
    ADD CONSTRAINT `vinyl_ibfk_1` FOREIGN KEY (`id_artist`) REFERENCES `artist` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
