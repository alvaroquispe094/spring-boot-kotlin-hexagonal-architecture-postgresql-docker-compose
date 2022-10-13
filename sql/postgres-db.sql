CREATE TABLE public.countries (
                                  id serial4 NOT NULL,
                                  "name" varchar(255) NULL,
                                  capital varchar NULL,
                                  subregion varchar(255) NULL,
                                  region varchar(255) NULL,
                                  population int4 NULL,
                                  "area" float4 NULL,
                                  currency varchar(50) NULL,
                                  "language" varchar(100) NULL,
                                  flag varchar(1000) NULL,
                                  CONSTRAINT countries_pkey PRIMARY KEY (id)
);

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Afghanistan', 'Kabul', 'Southern Asia', 'Asia', 40218234, 652230.0, 'Afghan afghani', 'Pashto', 'https://upload.wikimedia.org/wikipedia/commons/5/5c/Flag_of_the_Taliban.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Åland Islands', 'Mariehamn', 'Northern Europe', 'Europe', 28875, 1580.0, 'Euro', 'Swedish', 'https://flagcdn.com/ax.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Albania', 'Tirana', 'Southern Europe', 'Europe', 2837743, 28748.0, 'Albanian lek', 'Albanian', 'https://flagcdn.com/al.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Algeria', 'Algiers', 'Northern Africa', 'Africa', 44700000, 2381741.0, 'Algerian dinar', 'Arabic', 'https://flagcdn.com/dz.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('American Samoa', 'Pago Pago', 'Polynesia', 'Oceania', 55197, 199.0, 'United States Dollar', 'English', 'https://flagcdn.com/as.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Andorra', 'Andorra la Vella', 'Southern Europe', 'Europe', 77265, 468.0, 'Euro', 'Catalan', 'https://flagcdn.com/ad.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Angola', 'Luanda', 'Middle Africa', 'Africa', 32866268, 1246700.0, 'Angolan kwanza', 'Portuguese', 'https://flagcdn.com/ao.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Anguilla', 'The Valley', 'Caribbean', 'Americas', 13452, 91.0, 'East Caribbean dollar', 'English', 'https://flagcdn.com/ai.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Antarctica', '', 'Antarctica', 'Polar', 1000, 140000000.0, '', 'English', 'https://flagcdn.com/aq.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Antigua and Barbuda', 'Saint John''s', 'Caribbean', 'Americas', 97928, 442.0, 'East Caribbean dollar', 'English', 'https://flagcdn.com/ag.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Argentina', 'Buenos Aires', 'South America', 'Americas', 45376763, 2780400.0, 'Argentine peso', 'Spanish', 'https://flagcdn.com/ar.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Armenia', 'Yerevan', 'Western Asia', 'Asia', 2963234, 29743.0, 'Armenian dram', 'Armenian', 'https://flagcdn.com/am.svg');

INSERT INTO public.countries("name", capital, subregion, region, population, area, currency, "language", flag)
VALUES('Aruba', 'Oranjestad', 'Caribbean', 'Americas', 106766, 180.0, 'Aruban florin', 'Dutch', 'https://flagcdn.com/aw.svg');
