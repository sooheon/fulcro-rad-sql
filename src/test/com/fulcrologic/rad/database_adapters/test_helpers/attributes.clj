(ns com.fulcrologic.rad.database-adapters.test-helpers.attributes
  (:require
    [com.fulcrologic.rad.attributes            :as rad.attr :refer [defattr]]
    [com.fulcrologic.rad.database-adapters.sql :as rad.sql]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Account

(defattr account-id :account/id :uuid
  {::rad.attr/identity? true
   ::rad.sql/schema     :production
   ::rad.sql/table      "accounts"})


(defattr account-name :account/name :string
  {::rad.sql/schema     :production
   ::rad.sql/entity-ids #{:account/id}})


(defattr account-email :account/email :string
  {::rad.sql/schema     :production
   ::rad.sql/entity-ids #{:account/id}})


(defattr account-active? :account/active? :boolean
  {::rad.sql/schema       :production
   ::rad.sql/entity-ids   #{:account/id}
   ::rad.sql/column-name  "active"})


(defattr account-addresses :account/addresses :ref
  {::rad.attr/cardinality :many
   ::rad.attr/target      :address/id
   ::rad.sql/schema       :production
   ::rad.sql/entity-ids   #{:account/id} ;; Should always be one.
   ::rad.sql/join         ["addresses" "account_id"]
   })


;; Derived data
(defattr account-locked? :account/locked? :boolean
  {})


(def account-attributes
  [account-id account-name account-email account-active? account-locked? account-addresses])


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; User


(defattr user-id :user/id :uuid
  {::rad.sql/schema :production
   ::rad.sql/table  "users"})


(defattr user-name :user/name :string
  {::rad.sql/schema :production
   ::rad.sql/entity-ids #{:user/id}})


(def user-attributes [user-id user-name])


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Address

(defattr addr-id :address/id :uuid
  {::rad.attr/identity? true
   ::rad.sql/schema     :production
   ::rad.sql/table      "addresses"})


(defattr addr-street :address/street :string
  {::rad.sql/schema     :production
   ::rad.sql/entity-ids #{:address/id}})


(defattr addr-city :address/city :string
  {::rad.sql/schema     :production
   ::rad.sql/entity-ids #{:address/id}})


(def states #:state {:AZ "Arizona" :KS "Kansas" :MS "Mississippi"})

(defattr addr-state :address/state :enum
  {::rad.attr/enumerated-values (set (keys states))
   ::rad.attr/labels            states
   ::rad.sql/schema             :production
   ::rad.sql/entity-ids         #{:address/id}})


(defattr addr-zip :address/zip :string
  {::rad.sql/schema     :production
   ::rad.sql/entity-ids #{:address/id}})


(def address-attributes [addr-id addr-street addr-city addr-state addr-zip])

(def all-attributes
  (concat account-attributes user-attributes address-attributes))
